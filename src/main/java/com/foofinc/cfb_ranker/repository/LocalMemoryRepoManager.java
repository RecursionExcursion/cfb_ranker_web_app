package com.foofinc.cfb_ranker.repository;


import com.foofinc.cfb_ranker.repository.api.CfbApiAccess;
import com.foofinc.cfb_ranker.repository.model.SerializableSeason;
import com.foofinc.cfb_ranker.repository.persistence.SerializationManager;

/**
 * Checks local memory for file and loads the serialized data to minimize calls to the CFB API
 * If the file does not exist then this method will make the api calls and serialize the data into memory.
 * //TODO Implement ability to update based on date.
 */
class LocalMemoryRepoManager {

    static SerializableSeason retrieveSeason() {
        SerializationManager serializationManager = SerializationManager.INSTANCE;

        SerializableSeason season = serializationManager.loadSeason();
        if (!serializationManager.fileExists() || serializationManager.dataIsCorrupted()) {
            serializationManager.saveSeason(new CfbApiAccess().getSeason());
            season = serializationManager.loadSeason();
        }
        return season;
    }
}