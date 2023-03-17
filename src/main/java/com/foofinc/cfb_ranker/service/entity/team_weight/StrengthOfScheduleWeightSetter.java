package com.foofinc.cfb_ranker.service.entity.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.Comparator;
import java.util.List;

class StrengthOfScheduleWeightSetter extends WeightSetter {
    StrengthOfScheduleWeightSetter( List<StatisticizedTeam> teams) {
        super(teams);
    }

    @Override
    protected int getMultiplierWeight() {
        return statWeight.getStrengthOfScheduleWeight();
    }

    @Override
    protected List<StatisticizedTeam> rankTeams(List<StatisticizedTeam> teams) {
        return teams.stream()
                    .sorted(Comparator.comparingDouble(StatisticizedTeam::getStrengthOfSchedulePerGame))
                    .toList();
    }

    @Override
    protected StatRecord invokeGetters(StatisticizedTeam indexedTeam, StatisticizedTeam lastTeam) {
        return new StatRecord(indexedTeam.getStrengthOfSchedulePerGame(), lastTeam.getStrengthOfSchedulePerGame());
    }
}
