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
                        new WinWeightSetter(unweightedTeams).getWeightOfRankings(),
                        new LossWeightSetter(unweightedTeams).getWeightOfRankings(),

                        //Off/Def
                        new OffenseWeightSetter(unweightedTeams).getWeightOfRankings(),
                        new DefenseWeightSetter(unweightedTeams).getWeightOfRankings(),

                        //Points
                        new PointsForWeightSetter(unweightedTeams).getWeightOfRankings(),
                        new PointsAllowedWeightSetter(unweightedTeams).getWeightOfRankings()
                ));
    }

    public Map<StatisticizedTeam, Double> getPollInertiaMap(List<StatisticizedTeam> lastWeeksPolls) {
        return new PollInteriaWeightSetter(lastWeeksPolls).getWeightOfRankings();
    }

    public Map<StatisticizedTeam, Double> getStrengthOfScheduleMap(List<StatisticizedTeam> weightedTeams) {
        return new StrengthOfScheduleWeightSetter(weightedTeams).getWeightOfRankings();
    }
}
