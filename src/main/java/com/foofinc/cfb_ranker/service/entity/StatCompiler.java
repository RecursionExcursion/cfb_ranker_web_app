package com.foofinc.cfb_ranker.service.entity;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractStats;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractTeam;
import com.foofinc.cfb_ranker.repository.model.new_models.SerializableGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatCompiler
{
    public static void CompileStatsForTeam(SerializableGame game, StatisticizedTeam team) {

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
            teamYards =
                    Arrays.stream(thisTeam.getStats())
                          .filter(stat -> stat.getCategory().equals(totalYards))
                          .map(AbstractStats::getStat)
                            .findFirst().orElseThrow();

            oppYards =
                    Arrays.stream(oppTeam.getStats())
                          .filter(stat -> stat.getCategory().equals(totalYards))
                          .map(AbstractStats::getStat)
                          .findFirst().orElseThrow();

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
            //Replace with WebScraper?, currently manually handling errors
            String[] buffAkrWk14 = new String[]{
                    //Game ID
                    "401506450",
                    //Home Yards
                    "291",
                    //Away Yards
                    "306"
            };

            List<String[]> gamesWithoutStats = new ArrayList<>();
            gamesWithoutStats.add(buffAkrWk14);

            for (String[] game : gamesWithoutStats) {
                if (game[0].equals(gameId)) {
                    return homeOrAway.equals("H") ? game[1] : game[2];
                }
            }
            throw new Exception("Game was not found");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}

