package com.foofinc.cfb_ranker.repository.abstract_models;

import com.foofinc.cfb_ranker.repository.api.dto.StatsDto;

import java.io.Serializable;
import java.util.Arrays;

public abstract class AbstractTeam implements Serializable {
    protected String school;
    protected int points;
    protected StatsDto[] stats;

    public AbstractTeam() {
    }

    public AbstractTeam(String school, int points, StatsDto[] stats) {
        this.school = school;
        this.points = points;
        this.stats = stats;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public StatsDto[] getStats() {
        return stats;
    }

    public void setStats(StatsDto[] stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "Team{" +
                "school='" + school + '\'' +
                ", points=" + points +
                ", stats=" + Arrays.toString(stats) +
                '}';
    }
}
