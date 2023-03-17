package com.foofinc.cfb_ranker.service.entity.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.Comparator;
import java.util.List;

class WinWeightSetter extends WeightSetter {
    WinWeightSetter(List<StatisticizedTeam> teams) {
        super(teams);
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
