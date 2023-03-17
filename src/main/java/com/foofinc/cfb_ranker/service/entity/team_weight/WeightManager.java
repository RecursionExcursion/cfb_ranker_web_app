package com.foofinc.cfb_ranker.service.entity.team_weight;

import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeightManager {

    public List<Map<StatisticizedTeam, Double>> getWeightMapsList(List<StatisticizedTeam> unweightedTeams) {
        return new ArrayList<>(
                List.of(
                        //Wins/Losses
                        new WinWeightSetter(1, unweightedTeams).getWeightOfRankings(),
                        new LossWeighSetter(1, unweightedTeams).getWeightOfRankings(),

                        //Off/Def
                        new OffenseWeightSetter(1, unweightedTeams).getWeightOfRankings(),
                        new DefenseWeightSetter(1, unweightedTeams).getWeightOfRankings(),

                        //Points
                        new PointsForWeightSetter(1, unweightedTeams).getWeightOfRankings(),
                        new PointsAllowedWeightSetter(1, unweightedTeams).getWeightOfRankings()
                ));
    }

    public Map<StatisticizedTeam, Double> getPollInertiaMap(List<StatisticizedTeam> lastWeeksPolls) {
        return new PollInteriaWeightSetter(1, lastWeeksPolls).getWeightOfRankings();
    }

    public Map<StatisticizedTeam, Double> getStrengthOfScheduleMap(List<StatisticizedTeam> weightedTeams) {
        return new StrengthOfScheduleWeightSetter(1, weightedTeams).getWeightOfRankings();
    }
}
