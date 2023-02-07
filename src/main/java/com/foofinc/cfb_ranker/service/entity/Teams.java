package com.foofinc.cfb_ranker.service.entity;

import java.util.ArrayList;
import java.util.List;

/*
Singleton wrapper class
 */
public enum Teams {

    INSTANCE;

    private List<StatisticizedTeam> statisticizedTeams = new ArrayList<>();

    public List<StatisticizedTeam> getTeams() {
        return statisticizedTeams;
    }

    public void loadTeams(List<StatisticizedTeam> t) {
        statisticizedTeams = t;

    }

    @Override
    public String toString() {
        return statisticizedTeams.toString();
    }
}
