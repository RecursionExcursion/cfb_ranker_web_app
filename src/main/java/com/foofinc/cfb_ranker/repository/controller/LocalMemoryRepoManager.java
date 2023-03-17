package com.foofinc.cfb_ranker.repository.controller;


import com.foofinc.cfb_ranker.repository.api.CfbApiAccess;
import com.foofinc.cfb_ranker.repository.model.new_models.SerializableSeason;
import com.foofinc.cfb_ranker.repository.persistence.SerializationManager;

/**
 * Checks local memory for file and loads the serialized data to minimize calls to the CFB API
 * If the file does not exist then this method will make the api calls and serialize the data into memory.
 * //TODO Implement ability to update based on date.
 */
public enum LocalMemoryRepoManager {

    INSTANCE;

    public SerializableSeason retrieveData() {
        SerializableSeason season = SerializationManager.INSTANCE.loadSeason();
        if (SerializationManager.INSTANCE.dataIsCorrupted() || !SerializationManager.INSTANCE.fileExists()) {
            SerializationManager.INSTANCE.saveSeason(new CfbApiAccess().getSeason());
            season = SerializationManager.INSTANCE.loadSeason();
        }
        return season;
    }
}