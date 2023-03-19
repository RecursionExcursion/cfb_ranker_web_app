package com.foofinc.cfb_ranker.repository;

import com.foofinc.cfb_ranker.repository.model.SerializableSeason;

public enum StaticSeason {

    INSTANCE;


    private SerializableSeason season;

    StaticSeason() {
    }

    public SerializableSeason getSeason() {
        if (season == null) {
            season = retrieveData();
        }
        return season;
    }

    private SerializableSeason retrieveData() {
        return LocalMemoryRepoManager.retrieveSeason();
    }
}
