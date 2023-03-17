package com.foofinc.cfb_ranker.service.entity.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.Comparator;
import java.util.List;

class PointsAllowedWeightSetter extends WeightSetter {
    PointsAllowedWeightSetter(List<StatisticizedTeam> teams) {
        super(teams);
    }

    @Override
    protected int getMultiplierWeight() {
        return statWeight.getPointsAllowedWeight();
    }

    @Override
    protected List<StatisticizedTeam> rankTeams(List<StatisticizedTeam> teams) {
        return teams.stream()
                    .sorted(Comparator.comparingDouble(StatisticizedTeam::getPointsAllowedPerGame).reversed())
                    .toList();
    }

    @Override
    protected StatRecord invokeGetters(StatisticizedTeam indexedTeam, StatisticizedTeam lastTeam) {
        return new StatRecord(indexedTeam.getPointsAllowedPerGame(), lastTeam.getPointsAllowedPerGame());
    }
}
