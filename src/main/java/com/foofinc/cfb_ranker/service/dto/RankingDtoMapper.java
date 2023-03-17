package com.foofinc.cfb_ranker.service.dto;

import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.List;

public class RankingDtoMapper {

    private final List<List<StatisticizedTeam>> rankedTeams;


    public RankingDtoMapper(List<List<StatisticizedTeam>> rankedTeams) {
        this.rankedTeams = rankedTeams;
    }

    public RankingsDto mapRankingsToDto() {
        RankingsDto rankingsDto = new RankingsDto(rankedTeams.size(), rankedTeams.get(0)
                                                                                 .size());

        RankingsDto.SchoolDto[][] weeklyRankings = rankingsDto.getWeeklyRankings();


        for (int i = 0; i < rankedTeams.size(); i++) {
            for (int j = 0; j < rankedTeams.get(i)
                                           .size(); j++) {
                weeklyRankings[i][j] = mapTeamToDto(rankedTeams.get(i)
                                                               .get(j));
            }
        }

        rankingsDto.setWeeklyRankings(weeklyRankings);

        return rankingsDto;
    }

    private RankingsDto.SchoolDto mapTeamToDto(StatisticizedTeam statisticizedTeam2) {
        RankingsDto.SchoolDto schoolDto = new RankingsDto.SchoolDto();
        schoolDto.setSchoolName(statisticizedTeam2.getSchool()
                                                  .getSchool());
        schoolDto.setRank(statisticizedTeam2.getRank());
        schoolDto.setPointsForPerGame(statisticizedTeam2.getPointsForPerGame());
        schoolDto.setPointsAllowedPerGame(statisticizedTeam2.getPointsAllowedPerGame());
        schoolDto.setOffensePerGame(statisticizedTeam2.getOffensePerGame());
        schoolDto.setDefensePerGame(statisticizedTeam2.getDefensePerGame());
        schoolDto.setStrengthOfSchedulePerGame(statisticizedTeam2.getStrengthOfSchedulePerGame());
        schoolDto.setRecord(statisticizedTeam2.getRecord().toString());
        schoolDto.setWeight(statisticizedTeam2.getWeight());
        return schoolDto;
    }
}
