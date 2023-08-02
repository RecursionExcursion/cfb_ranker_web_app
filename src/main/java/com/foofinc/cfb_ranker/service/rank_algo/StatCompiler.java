package com.foofinc.cfb_ranker.service.rank_algo;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractStats;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractTeam;
import com.foofinc.cfb_ranker.repository.model.SerializableGame;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class StatCompiler {
    static void CompileStatsForTeam(SerializableGame game, StatisticizedTeam team) {

        AbstractTeam thisTeam = AbstractTeam.getTeamFromStatGame(game, team);
        AbstractTeam oppTeam = AbstractTeam.getOpponentFromStatGame(game, team);

        String totalYards = "totalYards";

        //Add game to schedule
        team.getSchedule().add(game);
        team.incGamesPlayed();

        //Allocate Points
        team.addToPointsFor(thisTeam.getPoints());
        team.addToPointsAllowed(oppTeam.getPoints());

        if (thisTeam.getPoints() > oppTeam.getPoints()) {
            team.getRecord().incWins();
        } else {
            team.getRecord().incLoses();
        }

        //Allocate Yards
        String teamYards;
        String oppYards;

        try {

            Function<AbstractTeam, String> getYardsFromAbstractTeam = (abstractTeam) ->
                    Arrays.stream(abstractTeam.getStats())
                          .filter(stat -> stat.getCategory().equals(totalYards))
                          .map(AbstractStats::getStat)
                          .findFirst().orElseThrow();

            teamYards = getYardsFromAbstractTeam.apply(thisTeam);
            oppYards = getYardsFromAbstractTeam.apply(oppTeam);


        } catch (Exception e) {
            String gameInfoString = String.format("Data is missing from a data structure, %s week %s",
                                                  game.getId(), game.getGameData().getWeek());
            System.out.println(gameInfoString);

            String teamHomeOrAway = team.getSchool().getId() == game.getGameData().getHome_id() ? "H" : "A";
            String oppHomeOrAway = teamHomeOrAway.equals("H") ? "A" : "H";

            String gameIdAsString = String.valueOf(game.getId());
            teamYards = GameNotComplete(gameIdAsString, teamHomeOrAway);
            oppYards = GameNotComplete(gameIdAsString, oppHomeOrAway);
        }
        assert teamYards != null;
        assert oppYards != null;
        team.addToTotalOffense(Integer.parseInt(teamYards));
        team.addToTotalDefense(Integer.parseInt(oppYards));
    }

    private static String GameNotComplete(String gameId, String homeOrAway) {
        try {

            record MissingGame(String id, String homeYards, String awayYards) {}

            //Replace with WebScraper?, currently manually handling errors
            MissingGame buffAkrWk14 = new MissingGame("401506450", "291", "306");

            List<MissingGame> gamesWithoutStats = List.of(buffAkrWk14);

            for (MissingGame game : gamesWithoutStats) {
                if (game.id.equals(gameId)) {
                    System.out.printf("%s has been added manually", game.id);
                    return homeOrAway.equals("H") ? game.homeYards : game.awayYards;
                }
            }
            throw new Exception("Game was not found");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}



