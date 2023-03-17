package com.foofinc.cfb_ranker.repository.model.new_models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableSeason implements Serializable {

    private List<SerializableSchool> schools;
    private List<SerializableGame> games;

    public SerializableSeason() {
        schools = new ArrayList<>();
        games= new ArrayList<>();
    }

    public List<SerializableSchool> getSchools() {
        return schools;
    }

    public void setSchools(List<SerializableSchool> schools) {
        this.schools = schools;
    }

    public List<SerializableGame> getGames() {
        return games;
    }

    public void setGames(List<SerializableGame> games) {
        this.games = games;
    }
}
