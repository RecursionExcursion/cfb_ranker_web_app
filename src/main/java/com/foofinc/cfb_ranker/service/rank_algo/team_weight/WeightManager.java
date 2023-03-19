package com.foofinc.cfb_ranker.service.rank_algo.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatWeight;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WeightManager {

    StatWeight statWeight;

    public WeightManager(StatWeight statWeight) {
        this.statWeight = statWeight;
    }

    public List<Map<StatisticizedTeam, Double>> getWeightMapsList(List<StatisticizedTeam> unweightedTeams) {
        WeightSetter[] weightSetters = new WeightSetter[]{
                //Wins/Losses
                new WinWeightSetter(unweightedTeams, statWeight),
                new LossWeightSetter(unweightedTeams, statWeight),
                //Off/Def
                new OffenseWeightSetter(unweightedTeams, statWeight),
                new DefenseWeightSetter(unweightedTeams, statWeight),
                //Points
                new PointsForWeightSetter(unweightedTeams, statWeight),
                new PointsAllowedWeightSetter(unweightedTeams, statWeight)
        };

        return Arrays.stream(weightSetters)
                     .map(WeightSetter::getWeightOfRankings)
                     .toList();
    }

    public Map<StatisticizedTeam, Double> getPollInertiaMap(List<StatisticizedTeam> lastWeeksPolls) {
        return new PollInteriaWeightSetter(lastWeeksPolls, statWeight).getWeightOfRankings();
    }

    public Map<StatisticizedTeam, Double> getStrengthOfScheduleMap(List<StatisticizedTeam> weightedTeams) {
        return new StrengthOfScheduleWeightSetter(weightedTeams, statWeight).getWeightOfRankings();
    }
}
