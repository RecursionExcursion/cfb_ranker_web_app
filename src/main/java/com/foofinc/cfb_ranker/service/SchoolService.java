package com.foofinc.cfb_ranker.service;

import com.foofinc.cfb_ranker.repository.SchoolsRepository;
import com.foofinc.cfb_ranker.service.entity.RankedSeason;
import com.foofinc.cfb_ranker.service.entity.StatWeight;
import com.foofinc.cfb_ranker.service.rank_algo.RankingAlgorithm;
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
        StatWeight statWeight = new StatWeight(6, 6,
                                               4, 4,
                                               1, 1,
                                               2, 5);
        return buildResponseDto(statWeight);
    }

    public ResponseDto getTeams(StatWeight statWeight) {
        return buildResponseDto(statWeight);
    }

    private ResponseDto buildResponseDto(StatWeight statWeight) {

        RankedSeason rankedSeason = new RankingAlgorithm(schoolsRepository.getTeams(), statWeight).getSeason();

        return new ResponseDto(statWeight, rankedSeason);
    }
}
