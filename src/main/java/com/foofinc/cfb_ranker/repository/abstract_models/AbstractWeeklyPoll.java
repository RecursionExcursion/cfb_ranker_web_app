package com.foofinc.cfb_ranker.repository.abstract_models;

import java.io.Serializable;

public abstract class AbstractWeeklyPoll implements Serializable {
    protected String season;
    protected String seasonType;
    protected int week;
    protected AbstractPollTeam[] pollTeams;

    public AbstractWeeklyPoll() {
    }

    public AbstractWeeklyPoll(String season, String seasonType, int week, AbstractPollTeam[] pollTeams) {
        this.season = season;
        this.seasonType = seasonType;
        this.week = week;
        this.pollTeams = pollTeams;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public AbstractPollTeam[] getPollTeams() {
        return pollTeams;
    }

    public void setPollTeams(AbstractPollTeam[] pollTeams) {
        this.pollTeams = pollTeams;
    }
}
