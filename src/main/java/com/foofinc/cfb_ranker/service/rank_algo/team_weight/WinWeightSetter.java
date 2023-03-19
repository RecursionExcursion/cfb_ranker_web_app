package com.foofinc.cfb_ranker.service.rank_algo.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatWeight;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.Comparator;
import java.util.List;

class WinWeightSetter extends WeightSetter {
    public WinWeightSetter(List<StatisticizedTeam> teams, StatWeight statWeight) {
        super(teams, statWeight);
    }

    @Override
    protected int getMultiplierWeight() {
        return statWeight.getWinWeight();
    }

    @Override
    protected List<StatisticizedTeam> rankTeams(List<StatisticizedTeam> teams) {
        return teams.stream()
                    .sorted(Comparator.comparingInt(StatisticizedTeam::getWins).reversed())
                    .toList();
    }

    @Override
    protected StatRecord invokeGetters(StatisticizedTeam indexedTeam, StatisticizedTeam lastTeam) {
        return new StatRecord(indexedTeam.getWins(), lastTeam.getWins());
    }
}
