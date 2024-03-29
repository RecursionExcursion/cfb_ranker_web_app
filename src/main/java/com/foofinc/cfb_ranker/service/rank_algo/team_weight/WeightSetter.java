package com.foofinc.cfb_ranker.service.rank_algo.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatWeight;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class WeightSetter {

    protected int multiplier;
    protected List<StatisticizedTeam> rankedTeams;
    protected StatWeight statWeight;

    WeightSetter(List<StatisticizedTeam> teams, StatWeight statWeight) {
        this.statWeight = statWeight;
        this.multiplier = getMultiplierWeight();
        this.rankedTeams = rankTeams(teams);
    }

    protected abstract int getMultiplierWeight();

    protected abstract List<StatisticizedTeam> rankTeams(List<StatisticizedTeam> teams);

    protected abstract StatRecord invokeGetters(StatisticizedTeam indexedTeam, StatisticizedTeam lastTeam);

    Map<StatisticizedTeam, Double> getWeightOfRankings() {

        Map<StatisticizedTeam, Double> teamWeightMap = new HashMap<>();

        try {
            //Placeholder for previous team
            StatisticizedTeam lastTeam = null;

            for (int i = 0, rankWeight = 1; i < rankedTeams.size(); i++) {
                //Team getting weighted
                StatisticizedTeam indexedTeam = rankedTeams.get(i);

                //Skips first team being weighted since there's nothing to compare to.
                if (lastTeam != null) {

                    StatRecord statRecord = invokeGetters(indexedTeam, lastTeam);

                    double lastTeamValue = statRecord.lastTeamStat();
                    double indexedTeamValue = statRecord.indexedTeamStat();

                    //If they are different, move weight index to current list index
                    if (lastTeamValue != indexedTeamValue) {
                        rankWeight = i + 1;
                    }
                }
                teamWeightMap.put(indexedTeam, (double) rankWeight * multiplier);
                lastTeam = indexedTeam;
            }
        } catch (SecurityException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return teamWeightMap;
    }
}