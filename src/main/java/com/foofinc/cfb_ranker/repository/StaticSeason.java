package com.foofinc.cfb_ranker.repository;

import com.foofinc.cfb_ranker.repository.api.CfbApiAccess;
import com.foofinc.cfb_ranker.repository.model.SerializableSeason;
import com.foofinc.cfb_ranker.repository.persistence.SerializationManager;

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
        SerializationManager serializationManager = SerializationManager.INSTANCE;

        SerializableSeason season = serializationManager.loadSeason();
        if (serializationManager.dataIsCorrupted() || !serializationManager.fileExists()) {
            serializationManager.saveSeason(new CfbApiAccess().getSeason());
            season = serializationManager.loadSeason();
        }
        return season;
    }
}
