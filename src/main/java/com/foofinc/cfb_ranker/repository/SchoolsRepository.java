package com.foofinc.cfb_ranker.repository;

import com.foofinc.cfb_ranker.repository.controller.LocalMemoryRepoManager;
import com.foofinc.cfb_ranker.repository.model.new_models.SerializableSeason;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolsRepository {

    LocalMemoryRepoManager localMemoryRepoManager = LocalMemoryRepoManager.INSTANCE;

    public SerializableSeason getTeams() {
        return localMemoryRepoManager.retrieveData();
    }
}
