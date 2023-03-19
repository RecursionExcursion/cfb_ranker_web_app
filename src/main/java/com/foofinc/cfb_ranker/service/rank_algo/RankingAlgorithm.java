package com.foofinc.cfb_ranker.service.rank_algo;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractTeam;
import com.foofinc.cfb_ranker.repository.model.SerializableGame;
import com.foofinc.cfb_ranker.repository.model.SerializableSchool;
import com.foofinc.cfb_ranker.repository.model.SerializableSeason;
import com.foofinc.cfb_ranker.service.entity.RankedSeason;
import com.foofinc.cfb_ranker.service.entity.StatWeight;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;
import com.foofinc.cfb_ranker.service.rank_algo.team_weight.WeightManager;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class RankingAlgorithm {

    private final StatWeight statWeight;
    private final RankedSeason rankedSeason = new RankedSeason();
    private final RankedSeason weightedRankedSeason = new RankedSeason();
    private final SerializableSeason season;

    public RankingAlgorithm(SerializableSeason season, StatWeight statWeight) {
        this.season = season;
        this.statWeight = statWeight;
    }

    public RankedSeason getSeason() {
        rank();
        return weightedRankedSeason;
    }

    private void rank(){
        int lastWeek = season.getGames().stream()
                             .map(g -> g.getGameData().getWeek())
                             .max(Comparator.naturalOrder()).orElse(-1);

        String regularSeasonString = "regular";
        String postSeasonString = "postseason";

        for (int i = 1; i <= lastWeek; i++) {
            rankedSeason.getWeeks().add(linkTeamsToGames(i, regularSeasonString));
        }
        if (season.getGames().stream()
                  .anyMatch(g -> g.getGameData().getSeason_type().equals(postSeasonString))) {
            rankedSeason.getWeeks().add(linkTeamsToGames(1, postSeasonString));
        }

        List<List<StatisticizedTeam>> weeks = rankedSeason.getWeeks();
        for (int i = 0; i < weeks.size(); i++) {
            List<StatisticizedTeam> week = weeks.get(i);
            weightedRankedSeason.getWeeks().add(setWeightAndRank(week, i));
        }
    }

    private List<StatisticizedTeam> setWeightAndRank(List<StatisticizedTeam> teams, int index) {

        WeightManager weightManager = new WeightManager(statWeight);

        //Set weight for base stats
        List<Map<StatisticizedTeam, Double>> weightMapsList = weightManager.getWeightMapsList(teams);

        //Poll Inertia
        Map<StatisticizedTeam, Double> pollInteriaMap = null;
        if (index != 0) {
            List<StatisticizedTeam> lastWeeksPolls = weightedRankedSeason.getWeeks().get(index - 1);
            pollInteriaMap = weightManager.getPollInertiaMap(lastWeeksPolls);
        }

        //Calc pre-strength of schedule weight
        List<StatisticizedTeam> weightedTeams = new ArrayList<>(teams);

        for (StatisticizedTeam weightedTeam : weightedTeams) {
            double weight = 0;
            //Iterate through weightMaps
            for (Map<StatisticizedTeam, Double> map : weightMapsList) {
                weight += map.get(weightedTeam);
            }
            if (index != 0) {
                Optional<StatisticizedTeam> first = pollInteriaMap.keySet()
                                                                  .stream()
                                                                  .filter(key -> key.getSchool()
                                                                                    .getId() == weightedTeam.getSchool()
                                                                                                            .getId())
                                                                  .findFirst();
                weight += pollInteriaMap.get(first.orElseThrow());
            }
            weightedTeam.setWeight(weight);
        }

        weightedTeams = rankTeamsByWeight(weightedTeams);

        calculateStrengthOfSchedule(weightedTeams);

        Map<StatisticizedTeam, Double> weightedTeamsMap = weightManager.getStrengthOfScheduleMap(weightedTeams);

        weightedTeams.forEach(wt -> wt.setWeight(wt.getWeight() + weightedTeamsMap.get(wt)));

        weightedTeams = rankTeamsByWeight(weightedTeams);

        setRankings(weightedTeams);

        return weightedTeams;
    }

    private static List<StatisticizedTeam> rankTeamsByWeight(List<StatisticizedTeam> weightedTeams) {
        return weightedTeams.stream()
                            .sorted(Comparator.comparingDouble(StatisticizedTeam::getWeight))
                            .toList();
    }

    private List<StatisticizedTeam> linkTeamsToGames(int i, String seasonType) {

        Function<Integer, List<StatisticizedTeam>> getStatTeams = (week) -> {

            List<StatisticizedTeam> teams = new ArrayList<>();

            if (rankedSeason.getWeeks().size() == 0) {
                season.getSchools()
                      .forEach(s -> teams.add(new StatisticizedTeam((SerializableSchool) s)));
            } else {
                rankedSeason.getWeeks()
                            .get(rankedSeason.getWeeks().size() - 1)
                            .forEach(st -> teams.add(new StatisticizedTeam(st)));
            }
            return teams;
        };

        List<StatisticizedTeam> teamsForWeek = getStatTeams.apply(i);

        Consumer<SerializableGame> compileStatsFromGame = game -> {
            int awayId = game.getGameData().getAway_id();
            int homeId = game.getGameData().getHome_id();
            List<StatisticizedTeam> teamsInGame =
                    teamsForWeek.stream()
                                .filter(t -> t.getSchool().getId() == awayId ||
                                        t.getSchool().getId() == homeId)
                                .toList();
            teamsInGame.forEach(t -> StatCompiler.CompileStatsForTeam(game, t));
        };

        //Filter games
        List<SerializableGame> games = season.getGames()
                                             .stream()
                                             .filter(g -> g.getGameData().getWeek() == i)
                                             .filter(g -> g.getGameData().getSeason_type().equals(seasonType))
                                             .map(SerializableGame::new)
                                             .toList();

        games.forEach(compileStatsFromGame);

        return teamsForWeek;
    }

    private void calculateStrengthOfSchedule(List<StatisticizedTeam> teams) {

        for (StatisticizedTeam team : teams) {

            int schedStrength = 0;

            List<SerializableGame> schedule = team.getSchedule();

            for (SerializableGame game : schedule) {

                AbstractTeam oppTeam = AbstractTeam.getOpponentFromStatGame(game, team);

                Optional<StatisticizedTeam> opponent = teams.stream()
                                                            .filter(t -> t.getSchool().getSchool()
                                                                          .equals(oppTeam.getSchool()))
                                                            .findFirst();

                int oppStrengthIndex = opponent.map(statisticizedTeam -> teams.indexOf(statisticizedTeam) + 1)
                                               .orElseGet(() -> teams.size() + 1);
                schedStrength += oppStrengthIndex;
            }
            team.setStrengthOfSchedule(team.getStrengthOfSchedule() + schedStrength);
        }
    }

    private void setRankings(List<StatisticizedTeam> rankings) {
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i)
                    .setRank(i + 1);
        }
    }
}