package com.foofinc.cfb_ranker.service.rank_algo.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatWeight;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PollInteriaWeightSetter extends WeightSetter {
    public PollInteriaWeightSetter(List<StatisticizedTeam> teams,
                                   StatWeight statWeight) {
        super(teams, statWeight);
    }

    @Override
    protected int getMultiplierWeight() {
        return statWeight.getPollInertiaWeight();
    }

    @Override
    protected List<StatisticizedTeam> rankTeams(List<StatisticizedTeam> teams) {
        return teams;
    }

    @Override
    protected StatRecord invokeGetters(StatisticizedTeam indexedTeam, StatisticizedTeam lastTeam) {
        return null;
    }

    @Override
    public Map<StatisticizedTeam, Double> getWeightOfRankings() {

        Map<StatisticizedTeam, Double> teamWeightMap = new HashMap<>();

        for (int i = 0, rankWeight = 1; i < rankedTeams.size(); i++) {
            StatisticizedTeam indexedTeam = rankedTeams.get(i);
            teamWeightMap.put(indexedTeam, (double) ((rankWeight + i) * multiplier));
        }

        return teamWeightMap;
    }
}
