package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map game from weekly games JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDto implements DTO {
    long id;
    private TeamDto[] teams;

    public FixtureDto(TeamDto[] teams) {
        this.teams = teams;
    }

    public FixtureDto() {
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
