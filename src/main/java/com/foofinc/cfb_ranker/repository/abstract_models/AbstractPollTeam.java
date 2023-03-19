package com.foofinc.cfb_ranker.repository.abstract_models;

import java.io.Serializable;

public abstract class AbstractPollTeam implements Serializable {
    protected int rank;
    protected String school;
    protected String conference;
    protected int firstPlaceVotes;
    protected int points;

    public AbstractPollTeam() {
    }

    public AbstractPollTeam(int rank, String school, String conference, int firstPlaceVotes, int points) {
        this.rank = rank;
        this.school = school;
        this.conference = conference;
        this.firstPlaceVotes = firstPlaceVotes;
        this.points = points;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public int getFirstPlaceVotes() {
        return firstPlaceVotes;
    }

    public void setFirstPlaceVotes(int firstPlaceVotes) {
        this.firstPlaceVotes = firstPlaceVotes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

