package com.foofinc.cfb_ranker.service.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingAlgo {

    private final Map<StatisticizedTeam, Double> teamWeightMap;
    private List<StatisticizedTeam> rankedTeams;
    private List<StatisticizedTeam> previousWeek;

    //Inner class
    private final RankingLists rankingLists = new RankingLists();

    /*
    These Strings are based as markers to initial logic, some reference and are used as method signatures for reflection
     */
    //Not method signatures, logic is local
    private final String getPollInertiaString = "pollInertia";
    //StatisticizedTeam method signatures
    private final String getWinsString = "getWins";
    private final String getPointsForString = "getPointsForPerGame";
    private final String getPointsAllowedString = "getPointsAllowedPerGame";
    private final String getTotalOffenseString = "getOffensePerGame";
    private final String getTotalDefenseString = "getDefensePerGame";
    private final String getStrengthOfScheduleString = "getStrengthOfSchedulePerGame";


    public RankingAlgo(List<StatisticizedTeam> statTeams) {
        teamWeightMap = initializeTeamWeightMap(statTeams);
    }

    public RankingAlgo(List<StatisticizedTeam> statTeams, List<StatisticizedTeam> previousWeek) {
        teamWeightMap = initializeTeamWeightMap(statTeams);
        this.previousWeek = previousWeek;
    }

    public Map<StatisticizedTeam, Double> initializeTeamWeightMap(List<StatisticizedTeam> statTeams) {
        Map<StatisticizedTeam, Double> teamDoubleMap = new HashMap<>();
        statTeams.forEach(t -> teamDoubleMap.put(t, 0.0));
        return teamDoubleMap;
    }

    public List<StatisticizedTeam> rankAndGetTeams() {

        //TODO Refactor
        rankAndWeightStats();
        rankedTeams = getRankedTeams();

        //TODO Calc SS against rankedTeamsList
        rankAndWeightSchedule();
        rankedTeams = getRankedTeams();
        setWeight();

        setRankings(rankedTeams);
        rankedTeams = rankedTeams.stream()
                                 .sorted(Comparator.comparingDouble(StatisticizedTeam::getWeight))
                                 .toList();
        return rankedTeams;
    }

    private void rankAndWeightStats() {
        rankTeamsByCategory();
        getWeightedRankings();
        setFinalWeight();
    }

    private void rankAndWeightSchedule() {
        rankedTeams.forEach(rt -> rt.calculateStrengthOfSchedulePerGame(rankedTeams));
        rankingLists.setTeamsRankedByStrengthOfSchedule(rankTeamsByMethodCalled(getStrengthOfScheduleString));
        getWeightOfRankings(rankingLists.getTeamsRankedByStrengthOfSchedule(), getStrengthOfScheduleString);
        setFinalWeight();
    }

    private void setFinalWeight() {
        rankingLists.setTeamsRankedByTotalWeight(teamWeightMap.entrySet()
                                                              .stream()
                                                              .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                                                              .toList());
    }

    private List<StatisticizedTeam> getRankedTeams() {
        return rankingLists.getTeamsRankedByTotalWeight()
                           .stream()
                           .map(Map.Entry::getKey)
                           .toList();
    }


    private void rankTeamsByCategory() {
        rankingLists.setTeamsRankedByRecord(rankTeamsByMethodCalled(getWinsString));
        rankingLists.setTeamsRankedByPointsFor(rankTeamsByMethodCalled(getPointsForString));
        rankingLists.setTeamsRankedByPointsAllowed(rankTeamsByMethodCalled(getPointsAllowedString));
        rankingLists.setTeamsRankedByTotalOffense(rankTeamsByMethodCalled(getTotalOffenseString));
        rankingLists.setTeamsRankedByTotalDefense(rankTeamsByMethodCalled(getTotalDefenseString));
        if (previousWeek != null) {
            rankingLists.setTeamsRankedByPollInertia(previousWeek);
        }
    }

    private void getWeightedRankings() {
        getWeightOfRankings(rankingLists.getTeamsRankedByRecord(), getWinsString);
        getWeightOfRankings(rankingLists.getTeamsRankedByPointsFor(), getPointsForString);
        getWeightOfRankings(rankingLists.getTeamsRankedByPointsAllowed(), getPointsAllowedString);
        getWeightOfRankings(rankingLists.getTeamsRankedByTotalOffense(), getTotalOffenseString);
        getWeightOfRankings(rankingLists.getTeamsRankedByTotalDefense(), getTotalDefenseString);
        if (previousWeek != null) {
            getWeightOfRankings(rankingLists.getTeamsRankedByPollInertia(), getPollInertiaString);
        }
        setWeight();
    }


    /*
    This method calculates the weight from all the smaller rankings and puts it into teamsAndWeightMap.
     */
    private void getWeightOfRankings(List<StatisticizedTeam> rankedTeams, String methodName) {

        int multiplier = 1;
        switch (methodName) {
            case getWinsString -> multiplier = WEIGHT.WINS.getVal();
            case getPointsForString -> multiplier = WEIGHT.POINTS_FOR.getVal();
            case getPointsAllowedString -> multiplier = WEIGHT.POINTS_ALLOWED.getVal();
            case getStrengthOfScheduleString -> multiplier = WEIGHT.STRENGTH_OF_SCHEDULE.getVal();
            case getTotalOffenseString -> multiplier = WEIGHT.OFFENSIVE_YARDS.getVal();
            case getTotalDefenseString -> multiplier = WEIGHT.DEFENSIVE_YARDS.getVal();
            case getPollInertiaString -> multiplier = WEIGHT.POLL_INERTIA.getVal();
        }

        if (methodName.equals(getPollInertiaString)) {

            for (int i = 0, rankWeight = 1; i < rankedTeams.size(); i++) {
                StatisticizedTeam indexedTeam = rankedTeams.get(i);
                teamWeightMap.put(indexedTeam, teamWeightMap.get(indexedTeam) + (rankWeight * multiplier));
            }
        } else {
            try {
                //Placeholder for previous team
                StatisticizedTeam lastTeam = null;

                for (int i = 0, rankWeight = 1; i < rankedTeams.size(); i++) {
                    //Team getting weighted
                    StatisticizedTeam indexedTeam = rankedTeams.get(i);

                    //Skips first team being weighted since there's nothing to compare to.
                    if (lastTeam != null) {

                        //Get method signature from String param
                        Method method = StatisticizedTeam.class.getMethod(methodName);

                        //Get stat of this team and previous team
                        double lastTeamValue = Double.parseDouble(String.valueOf(method.invoke(lastTeam)));
                        double indexedTeamValue = Double.parseDouble(String.valueOf(method.invoke(indexedTeam)));

                        //If they are different, move weight index to current list index
                        if (lastTeamValue != indexedTeamValue) {
                            rankWeight = i + 1;
                        }
                    }
                    teamWeightMap.put(indexedTeam, teamWeightMap.get(indexedTeam) + (rankWeight * multiplier));
                    lastTeam = indexedTeam;
                }
            } catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<StatisticizedTeam> rankTeamsByMethodCalled(String methodName) {

        return switch (methodName) {
            case getWinsString -> teamWeightMap.keySet()
                                               .stream()
                                               .sorted(Comparator.comparing(StatisticizedTeam::getWins)
                                                                 .reversed())
                                               .toList();

            case getPointsForString -> teamWeightMap.keySet()
                                                    .stream()
                                                    .sorted(Comparator.comparing(StatisticizedTeam::getPointsForPerGame)
                                                                      .reversed())
                                                    .toList();

            case getPointsAllowedString -> teamWeightMap.keySet()
                                                        .stream()
                                                        .sorted(Comparator.comparing(StatisticizedTeam::getPointsAllowedPerGame))
                                                        .toList();

            case getTotalOffenseString -> teamWeightMap.keySet()
                                                       .stream()
                                                       .sorted(Comparator.comparing(StatisticizedTeam::getOffensePerGame)
                                                                         .reversed())
                                                       .toList();

            case getTotalDefenseString -> teamWeightMap.keySet()
                                                       .stream()
                                                       .sorted(Comparator.comparing(StatisticizedTeam::getDefensePerGame))
                                                       .toList();

            case getStrengthOfScheduleString -> teamWeightMap.keySet()
                                                             .stream()
                                                             .sorted(Comparator.comparing(StatisticizedTeam::getStrengthOfSchedulePerGame))
                                                             .toList();

            default ->
                    throw new IllegalArgumentException("Method does not exist for object of type 'StatisticizedTeam'");
        };
    }

    private void setWeight() {
        teamWeightMap.forEach(StatisticizedTeam::setWeight);
    }

    private void setRankings(List<StatisticizedTeam> rankings) {
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i)
                    .setRank(i + 1);
        }
    }

    @Override
    public String toString() {
        return rankedTeams.toString();
    }

    static class RankingLists {

        private List<StatisticizedTeam> teamsRankedByRecord;
        private List<StatisticizedTeam> teamsRankedByPointsFor;
        private List<StatisticizedTeam> teamsRankedByPointsAllowed;
        private List<StatisticizedTeam> teamsRankedByTotalOffense;
        private List<StatisticizedTeam> teamsRankedByTotalDefense;
        private List<StatisticizedTeam> teamsRankedByStrengthOfSchedule;
        private List<StatisticizedTeam> teamsRankedByPollInertia;

        private List<Map.Entry<StatisticizedTeam, Double>> teamsRankedByTotalWeight;

        List<StatisticizedTeam> getTeamsRankedByRecord() {
            return teamsRankedByRecord;
        }

        void setTeamsRankedByRecord(List<StatisticizedTeam> teamsRankedByRecord) {
            this.teamsRankedByRecord = teamsRankedByRecord;
        }

        List<StatisticizedTeam> getTeamsRankedByPointsFor() {
            return teamsRankedByPointsFor;
        }

        void setTeamsRankedByPointsFor(List<StatisticizedTeam> teamsRankedByPointsFor) {
            this.teamsRankedByPointsFor = teamsRankedByPointsFor;
        }

        List<StatisticizedTeam> getTeamsRankedByPointsAllowed() {
            return teamsRankedByPointsAllowed;
        }

        void setTeamsRankedByPointsAllowed(List<StatisticizedTeam> teamsRankedByPointsAllowed) {
            this.teamsRankedByPointsAllowed = teamsRankedByPointsAllowed;
        }

        List<StatisticizedTeam> getTeamsRankedByTotalOffense() {
            return teamsRankedByTotalOffense;
        }

        void setTeamsRankedByTotalOffense(List<StatisticizedTeam> teamsRankedByTotalOffense) {
            this.teamsRankedByTotalOffense = teamsRankedByTotalOffense;
        }

        List<StatisticizedTeam> getTeamsRankedByTotalDefense() {
            return teamsRankedByTotalDefense;
        }

        void setTeamsRankedByTotalDefense(List<StatisticizedTeam> teamsRankedByTotalDefense) {
            this.teamsRankedByTotalDefense = teamsRankedByTotalDefense;
        }

        List<StatisticizedTeam> getTeamsRankedByStrengthOfSchedule() {
            return teamsRankedByStrengthOfSchedule;
        }

        void setTeamsRankedByStrengthOfSchedule(List<StatisticizedTeam> teamsRankedByStrengthOfSchedule) {
            this.teamsRankedByStrengthOfSchedule = teamsRankedByStrengthOfSchedule;
        }

        public List<StatisticizedTeam> getTeamsRankedByPollInertia() {
            return teamsRankedByPollInertia;
        }

        public void setTeamsRankedByPollInertia(List<StatisticizedTeam> teamsRankedByPollInertia) {
            this.teamsRankedByPollInertia = teamsRankedByPollInertia;
        }

        List<Map.Entry<StatisticizedTeam, Double>> getTeamsRankedByTotalWeight() {
            return teamsRankedByTotalWeight;
        }

        void setTeamsRankedByTotalWeight(List<Map.Entry<StatisticizedTeam, Double>> teamsRankedByTotalWeight) {
            this.teamsRankedByTotalWeight = teamsRankedByTotalWeight;
        }
    }
}
