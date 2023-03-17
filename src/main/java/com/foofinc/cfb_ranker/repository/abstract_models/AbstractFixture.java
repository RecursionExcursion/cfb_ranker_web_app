package com.foofinc.cfb_ranker.repository.abstract_models;

import com.foofinc.cfb_ranker.repository.api.dto.TeamDto;

import java.io.Serializable;
import java.util.Arrays;

public abstract class AbstractFixture implements Serializable {
    protected long id;
    protected TeamDto[] teams;

    public AbstractFixture(TeamDto[] teams) {
        this.teams = teams;
    }

    public AbstractFixture() {
    }

    public TeamDto[] getTeams() {
        return teams;
    }

    public void setTeams(TeamDto[] teams) {
        this.teams = teams;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fixture{" +
                "teams=" + Arrays.toString(teams) +
                '}';
    }
}
