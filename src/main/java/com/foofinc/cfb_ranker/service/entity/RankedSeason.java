package com.foofinc.cfb_ranker.service.entity;

import java.util.ArrayList;
import java.util.List;

public class RankedSeason {

    private final List<List<StatisticizedTeam>> weeks = new ArrayList<>();

    public List<List<StatisticizedTeam>> getWeeks() {
        return weeks;
    }
}
