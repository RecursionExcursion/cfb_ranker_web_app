package com.foofinc.cfb_ranker.repository;

import com.foofinc.cfb_ranker.repository.controller.LocalMemoryRepoController;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam_2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchoolsRepository {

    LocalMemoryRepoController localMemoryRepoController = LocalMemoryRepoController.getInstance();

    public List<List<StatisticizedTeam_2>> getTeams() {
        return localMemoryRepoController.retrieveData();
    }
}
