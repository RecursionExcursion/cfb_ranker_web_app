package com.foofinc.cfb_ranker.repository;

import com.foofinc.cfb_ranker.repository.controller.LocalMemoryRepoManager;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchoolsRepository {

    LocalMemoryRepoManager localMemoryRepoManager = LocalMemoryRepoManager.getInstance();

    public List<List<StatisticizedTeam>> getTeams() {
        return localMemoryRepoManager.retrieveData();
    }
}
