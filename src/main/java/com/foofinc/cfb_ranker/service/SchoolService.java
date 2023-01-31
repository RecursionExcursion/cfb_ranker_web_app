package com.foofinc.cfb_ranker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfb_ranker.repository.InMemoryRepo;
import com.foofinc.cfb_ranker.service.dto.RankingDtoMapper;
import com.foofinc.cfb_ranker.service.dto.RankingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SchoolService {

    private final InMemoryRepo inMemoryRepo;

    @Autowired
    public SchoolService(InMemoryRepo inMemoryRepo) {
        this.inMemoryRepo = inMemoryRepo;
    }

    public String getTeams() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RankingsDto rankingsDto = new RankingDtoMapper(inMemoryRepo.getTeams()).mapRankingsToDto();
            return objectMapper.writeValueAsString(rankingsDto);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while mapping object to JSON");
        }
    }
}
