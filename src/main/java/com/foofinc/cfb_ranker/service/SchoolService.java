package com.foofinc.cfb_ranker.service;

import com.foofinc.cfb_ranker.repository.SchoolsRepository;
import com.foofinc.cfb_ranker.service.dto.RankingDtoMapper;
import com.foofinc.cfb_ranker.service.dto.RankingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    private final SchoolsRepository schoolsRepository;

    @Autowired
    public SchoolService(SchoolsRepository schoolsRepository) {
        this.schoolsRepository = schoolsRepository;
    }

    public RankingsDto getTeams() {
        return new RankingDtoMapper(schoolsRepository.getTeams()).mapRankingsToDto();
    }
}
