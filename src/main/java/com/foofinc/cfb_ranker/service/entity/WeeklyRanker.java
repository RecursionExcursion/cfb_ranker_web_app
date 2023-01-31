package com.foofinc.cfb_ranker.service.entity;


import com.foofinc.cfb_ranker.repository.model.Game;
import com.foofinc.cfb_ranker.repository.model.School;
import com.foofinc.cfb_ranker.repository.model.SchoolList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
This class is the head of the ranking logic
This class creates a List of StatTeam Lists to hold the data.
The index of the team in the list relates to their ranking as well as the index in the outer list relates to the week
 of the season.
The teams are ranked according to their stats and then ranked once again based after the strength of schedule is
 ranked and taken into consideration based off the former ranking result.
Poll inertia is in reference to the teams rating the previous week;

 */

public class WeeklyRanker {

    List<School> schools = SchoolList.INSTANCE.getSchoolsAsList();


    List<StatisticizedTeam_2> statisticizedTeam_2List = schools.stream()
                                                               .map(StatisticizedTeam_2::createTeam)
                                                               .toList();


    List<List<StatisticizedTeam_2>> weeklyRankings = new ArrayList<>();

    public List<List<StatisticizedTeam_2>> getRankings() {
        Teams_2.INSTANCE.loadTeams(statisticizedTeam_2List);
        initialize();
        return weeklyRankings;
    }

    private void initialize() {
        List<StatisticizedTeam_2> lastWeek = null;

        String regularSeason = "regular";
        String postSeason = "postseason";

        for (int i = 1; i <= 16; i++) {
            rankTeamsForWeek(i, regularSeason);
            rankAndAddTeamsToList(lastWeek);
            lastWeek = weeklyRankings.get(weeklyRankings.size() - 1);
        }
        rankTeamsForWeek(1, postSeason);
        rankAndAddTeamsToList(lastWeek);
    }

    private void rankAndAddTeamsToList(List<StatisticizedTeam_2> lastWeek) {
        List<StatisticizedTeam_2> statisticizedTeam2s =
                new RankingAlgo_2(statisticizedTeam_2List, lastWeek).rankAndGetTeams();
        weeklyRankings.add(statisticizedTeam2s);
    }


    public void rankTeamsForWeek(int week, String seasonType) {
        List<Game> gamesForWeek = getGamesForWeek(week, seasonType);

        for (Game g : gamesForWeek) {
            StatisticizedTeam_2 homeTeam = findStatTeamFromSchool(g.getHome());
            StatisticizedTeam_2 awayTeam = findStatTeamFromSchool(g.getAway());

            if (homeTeam != null) {
                new GameToStatTeamMapper_2(g, homeTeam);
            }
            if (awayTeam != null) {
                new GameToStatTeamMapper_2(g, awayTeam);
            }
        }


    }

    private StatisticizedTeam_2 findStatTeamFromSchool(School school) {
        for (StatisticizedTeam_2 st : statisticizedTeam_2List) {
            if (st.getSchool() == school) return st;
        }
        return null;
    }


    private List<Game> getGamesForWeek(int week, String seasonType) {

        Set<Game> games2 = new HashSet<>();
        for (School school : schools) {
            List<Game> schedule = school.getSchedule();
            for (Game game : schedule) {
                if (game.getWeek() == week && game.getSeason_type()
                                                  .equals(seasonType)) {
                    games2.add(game);
                }
            }
        }
        return new ArrayList<>(games2);
    }
}
