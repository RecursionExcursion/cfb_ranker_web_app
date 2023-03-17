package com.foofinc.cfb_ranker.repository;

import com.foofinc.cfb_ranker.repository.model.SerializableSeason;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolsRepository {

    public SerializableSeason getTeams() {
        return LocalMemoryRepoManager.retrieveData();
    }
}
