package com.foofinc.cfb_ranker.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractFixture;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractGameData;
import com.foofinc.cfb_ranker.repository.api.dto.*;
import com.foofinc.cfb_ranker.repository.model.SerializableGame;
import com.foofinc.cfb_ranker.repository.model.SerializableSchool;
import com.foofinc.cfb_ranker.repository.model.SerializableSeason;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CfbApiAccess {

    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";
    private final String teamGamesUrlString = "https://api.collegefootballdata.com/games/teams?year=2022"/*&seasonType=regular&week=#*/;
    private final String[] gamesUrlString =
            {"https://api.collegefootballdata.com/games?year=2022" /*&week=#&seasonType=regular*/, "&division=fbs"};

    //TODO Unimplemented API Call
    private final String week1PollsUrlString =
            "https://api.collegefootballdata.com/rankings?year=2022&week=1&seasonType=regular";

    private final ObjectMapper mapper = new ObjectMapper();

    private final SerializableSeason season = new SerializableSeason();

    public CfbApiAccess() {
        loadSchoolsIntoSeason();
        loadGamesIntoSeason();
    }

    public SerializableSeason getSeason() {
        return season;
    }

    //TODO Executor Service to handle each request?

    private void loadSchoolsIntoSeason() {
        SchoolDto[] schoolDtos = sendGetRequest(SchoolDto[].class);
        for (SchoolDto dto : schoolDtos) {
            season.getSchools()
                  .add(new SerializableSchool(dto));
        }
    }

    private void loadGamesIntoSeason() {
        List<CompleteGameDto> gamesForSeason = getGamesForSeason();
        for (CompleteGameDto dto : gamesForSeason) {
            season.getGames()
                  .add(new SerializableGame(dto));
        }
    }

    /*
    Makes number of [weeks in season] + [1 postseason] calls to '/games/teams' and /games
    Returns a list containing maps representing week# that hold data from FixtureDto and GameDataDto wrapped in a
    CompleteGameDto.
     */
    private List<CompleteGameDto> getGamesForSeason() {
        String regSeason = "&seasonType=regular";
        String postSeason = "&seasonType=postseason";

        List<CompleteGameDto> gameList = new ArrayList<>();

        //Regular Season
        int startingWeek = 1, weeksInSeason = 15;
        for (int i = startingWeek; i <= weeksInSeason; i++) {

            List<CompleteGameDto> dtoList = mapToCompleteGameDtoList(regSeason, i);
            if (dtoList == null) break;
            gameList.addAll(dtoList);
        }

        List<CompleteGameDto> dtoList = mapToCompleteGameDtoList(postSeason, 1);
        if (dtoList != null) {
            gameList.addAll(dtoList);
        }

        return gameList;
    }

    private List<CompleteGameDto> mapToCompleteGameDtoList(String season, int i) {

        AbstractFixture[] tempFixDto = sendGetRequest(i, season, FixtureDto[].class);
        AbstractGameData[] tempGameDataDto = sendGetRequest(i, season, GameDataDto[].class);

        if (tempFixDto.length == 0) return null;

        final List<CompleteGameDto> tempList = new ArrayList<>();

        for (AbstractFixture fixDto : tempFixDto) {

            TeamDto[] teams = fixDto.getTeams();

            long count = this.season.getSchools()
                                    .stream()
                                    .filter(t -> t.getSchool().equals(teams[0].getSchool()) ||
                                            t.getSchool().equals(teams[1].getSchool())).count();


            if (count != 0) {
                CompleteGameDto dto = new CompleteGameDto();
                dto.setId(fixDto.getId());
                dto.setFixture(fixDto);
                tempList.add(dto);
            }
        }

        for (AbstractGameData gameDto : tempGameDataDto) {

            Optional<CompleteGameDto> gameOptional = tempList.stream()
                                                             .filter(g -> g.getId() == gameDto.getId())
                                                             .findAny();

            if (gameOptional.isPresent()) {
                CompleteGameDto completeGameDto = gameOptional.get();
                completeGameDto.setGameData(gameDto);
            }
        }

        return tempList;
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
