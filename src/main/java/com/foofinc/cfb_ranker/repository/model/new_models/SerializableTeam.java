package com.foofinc.cfb_ranker.repository.model.new_models;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractTeam;
import com.foofinc.cfb_ranker.repository.api.dto.StatsDto;

import java.io.Serializable;

public class SerializableTeam extends AbstractTeam implements Serializable {

    public SerializableTeam(String school, int points, StatsDto[] stats) {
        super(school, points, stats);
    }

    public SerializableTeam(AbstractTeam team) {
        school = team.getSchool();
        points = team.getPoints();
        stats = team.getStats();
    }
}
