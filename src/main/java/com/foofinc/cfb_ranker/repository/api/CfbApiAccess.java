package com.foofinc.cfb_ranker.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfb_ranker.repository.api.dto.CompleteGameDto;
import com.foofinc.cfb_ranker.repository.api.dto.FixtureDto;
import com.foofinc.cfb_ranker.repository.api.dto.GameDataDto;
import com.foofinc.cfb_ranker.repository.api.dto.SchoolDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CfbApiAccess {

    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";
    private final String teamGamesUrlString = "https://api.collegefootballdata.com/games/teams?year=2022"/*&seasonType=regular&week=#*/;
    private final String[] gamesUrlString =
            {"https://api.collegefootballdata.com/games?year=2022" /*&week=#&seasonType=regular*/, "&division=fbs"};
    private final List<SchoolDto> schools;
    private final List<Map<Long, CompleteGameDto>> weeks;
    private final ObjectMapper mapper = new ObjectMapper();

    public CfbApiAccess() {
        schools = getAllSchools();
        weeks = getFixturesForSeason();
    }

    public List<Map<Long, CompleteGameDto>> getWeeks() {
        return List.copyOf(weeks);
    }

    public List<SchoolDto> getSchools() {
        return List.copyOf(schools);
    }

    private List<SchoolDto> getAllSchools() {
        return List.of(sendGetRequest(SchoolDto[].class));
    }

    /*
    Makes number of [weeks in season] + [1 postseason] calls to '/games/teams' and /games
    Returns a list containing maps representing week# that hold data from FixtureDto and GameDataDto wrapped in a
    CompleteGameDto.
     */
    private List<Map<Long, CompleteGameDto>> getFixturesForSeason() {
        String regSeason = "&seasonType=regular";
        String postSeason = "&seasonType=postseason";

        List<Map<Long, CompleteGameDto>> completeGameMap = new ArrayList<>();

        //Regular Season
        int startingWeek = 1, weeksInSeason = 15;
        for (int i = startingWeek; i <= weeksInSeason; i++) {
            Map<Long, CompleteGameDto> finalListMapCopy = mapToCompleteGameDto(regSeason, i);
            if (finalListMapCopy == null) break;
            completeGameMap.add(finalListMapCopy);
        }

        //Post Season
        Map<Long, CompleteGameDto> finalListMapCopy = mapToCompleteGameDto(postSeason, 1);
        completeGameMap.add(finalListMapCopy);


        return completeGameMap;
    }

    private Map<Long, CompleteGameDto> mapToCompleteGameDto(String season, int i) {
        FixtureDto[] tempFixDto = sendGetRequest(i, season, FixtureDto[].class);
        GameDataDto[] tempGameDataDto = sendGetRequest(i, season, GameDataDto[].class);
        if (tempFixDto.length == 0) {
            return null;
        }

        final Map<Long, CompleteGameDto> tempMap = new HashMap<>();
        for (FixtureDto fixDto : tempFixDto) {
            tempMap.put(fixDto.getId(), new CompleteGameDto(fixDto.getId(), fixDto));
        }
        for (GameDataDto gameDto : tempGameDataDto) {
            if (tempMap.containsKey(gameDto.getId())) {
                tempMap.get(gameDto.getId())
                       .setGameDataDto(gameDto);
            }
        }

        return tempMap.keySet()
                      .stream()
                      .filter(key -> !tempMap.get(key)
                                             .hasNullData())
                      .collect(Collectors.toMap(id -> id, tempMap::get));
    }

    private <T> T sendGetRequest(int week, String seasonType, Class<T> t) {
        try {
            String getRequestString;
            if (t == GameDataDto[].class) {
                getRequestString = gamesUrlString[0] + "&week=" + week + seasonType + gamesUrlString[1];
            } else if (t == FixtureDto[].class) {
                getRequestString = teamGamesUrlString + seasonType + "&week=" + week;
            } else if (t == SchoolDto[].class) {
                getRequestString = schoolsUrlString;
            } else {
                throw new IllegalArgumentException("Cannot not create Dto.Class from Get request");
            }
            return mapDataFromJSON(getJSONFromAPI(getRequestString), t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T sendGetRequest(Class<T> t) {
        return sendGetRequest(-1, null, t);
    }

    private <T> T mapDataFromJSON(String jsonString, Class<T> t) throws JsonProcessingException {
        return mapper.readValue(jsonString, t);
    }

    private String getJSONFromAPI(String url) throws IOException {
        String bearer = "gLQdG5n7YtiTjzu/bxxxd+rdzzrhWftHTtIH7PAGVWlAQMOAA7h2ria3ai2Dl9zc";
        return APICaller.INSTANCE.getJSONFromAPICall(url, bearer);
    }
}
