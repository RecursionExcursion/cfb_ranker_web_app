package com.foofinc.cfb_ranker.repository.abstract_models;

import com.foofinc.cfb_ranker.repository.api.dto.StatsDto;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

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

    public static AbstractTeam getTeamFromStatGame(AbstractGame game, StatisticizedTeam team) {
        return Arrays.stream(game.getFixture().getTeams())
                     .filter(t -> t.getSchool().equals(team.getSchool().getSchool()))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Cannot find team"));
    }

    public static AbstractTeam getOpponentFromStatGame(AbstractGame game, StatisticizedTeam team) {
        return Arrays.stream(game.getFixture().getTeams())
                     .filter(t -> !t.getSchool().equals(team.getSchool().getSchool()))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Cannot find team"));
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
