package com.foofinc.cfb_ranker.repository.model.new_models;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractFixture;
import com.foofinc.cfb_ranker.repository.api.dto.TeamDto;

import java.io.Serializable;

public class SerializableFixture extends AbstractFixture implements Serializable {

    public SerializableFixture(TeamDto[] teams) {
        super(teams);
    }

    public SerializableFixture(AbstractFixture fixture) {
        id = fixture.getId();
        teams = fixture.getTeams();
    }
}
