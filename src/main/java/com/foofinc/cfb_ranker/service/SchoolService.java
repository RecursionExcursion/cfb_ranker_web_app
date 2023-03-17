package com.foofinc.cfb_ranker.service;

import com.foofinc.cfb_ranker.repository.SchoolsRepository;
import com.foofinc.cfb_ranker.repository.model.new_models.SerializableSeason;
import com.foofinc.cfb_ranker.service.entity.RankedSeason;
import com.foofinc.cfb_ranker.service.entity.RankingAlgo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    private final SchoolsRepository schoolsRepository;

    @Autowired
    public SchoolService(SchoolsRepository schoolsRepository) {
        this.schoolsRepository = schoolsRepository;
    }

    public RankedSeason getTeams() {
        SerializableSeason teams = schoolsRepository.getTeams();
        RankedSeason rankedSeason = new RankingAlgo2(teams).rankAndGetTeams();
        return rankedSeason;
//        return new RankingDtoMapper(rankedSeason).mapRankingsToDto();
    }
}
