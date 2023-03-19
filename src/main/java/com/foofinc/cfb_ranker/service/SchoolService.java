package com.foofinc.cfb_ranker.service;

import com.foofinc.cfb_ranker.repository.SchoolsRepository;
import com.foofinc.cfb_ranker.repository.model.SerializableSeason;
import com.foofinc.cfb_ranker.service.entity.RankedSeason;
import com.foofinc.cfb_ranker.service.rank_algo.RankingAlgorithm;
import com.foofinc.cfb_ranker.service.entity.StatWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    private final SchoolsRepository schoolsRepository;

    @Autowired
    public SchoolService(SchoolsRepository schoolsRepository) {
        this.schoolsRepository = schoolsRepository;
    }

    public ResponseDto getTeams() {
        SerializableSeason teams = schoolsRepository.getTeams();

        StatWeight statWeight = new StatWeight(6, 6,
                                               4, 4,
                                               1, 1,
                                               2, 5);

        RankedSeason rankedSeason = new RankingAlgorithm(teams, statWeight).rankAndGetTeams();

        ResponseDto dto = new ResponseDto(statWeight, rankedSeason);

        return dto;
    }

    public ResponseDto getTeams(StatWeight statWeight) {
        SerializableSeason teams = schoolsRepository.getTeams();

        RankedSeason rankedSeason = new RankingAlgorithm(teams, statWeight).rankAndGetTeams();

        ResponseDto dto = new ResponseDto(statWeight, rankedSeason);

        return dto;

    }
}
