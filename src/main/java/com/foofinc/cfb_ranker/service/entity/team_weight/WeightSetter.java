package com.foofinc.cfb_ranker.service.entity.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WeightSetter {

    protected int multiplier;
    protected List<StatisticizedTeam> rankedTeams;

    public WeightSetter(int multiplier, List<StatisticizedTeam> teams) {
        this.multiplier = multiplier;
        this.rankedTeams = rankTeams(teams);
    }

    protected abstract List<StatisticizedTeam> rankTeams(List<StatisticizedTeam> teams);

    protected abstract StatRecord invokeGetters(StatisticizedTeam indexedTeam, StatisticizedTeam lastTeam);


    public Map<StatisticizedTeam, Double> getWeightOfRankings() {

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