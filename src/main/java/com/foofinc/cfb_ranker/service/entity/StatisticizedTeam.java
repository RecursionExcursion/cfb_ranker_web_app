package com.foofinc.cfb_ranker.service.entity;


import com.foofinc.cfb_ranker.repository.model.SerializableGame;
import com.foofinc.cfb_ranker.repository.model.SerializableSchool;

import java.util.ArrayList;
import java.util.List;

public class StatisticizedTeam {

    private final SerializableSchool school;
    private int rank;
    private final Record record;
    private Double weight;

    private int totalOffense;
    private int totalDefense;

    private int pointsAllowed;
    private int pointsFor;

    private int strengthOfSchedule;

    private int gamesPlayed;

    private final List<SerializableGame> schedule;

    public StatisticizedTeam(SerializableSchool school) {
        this.school = school;
        rank = 0;
        record = new Record();
        totalOffense = 0;
        totalDefense = 0;
        pointsAllowed = 0;
        pointsFor = 0;
        strengthOfSchedule = 0;
        weight = 0.0;
        gamesPlayed = 0;
        schedule = new ArrayList<>();
    }

    public StatisticizedTeam(StatisticizedTeam oldTeam) {
        this.school = oldTeam.getSchool();
        this.rank = 0;
        this.record = new Record(oldTeam.getRecord().getWins(), oldTeam.getRecord().getLosses());
        this.totalOffense = oldTeam.getTotalOffense();
        this.totalDefense = oldTeam.getTotalDefense();
        this.pointsAllowed = oldTeam.getPointsAllowed();
        this.pointsFor = oldTeam.getPointsFor();
        this.strengthOfSchedule = -1;
        this.weight = 0.0;
        this.gamesPlayed = oldTeam.getGamesPlayed();
        schedule = new ArrayList<>(oldTeam.getSchedule());
    }

    public SerializableSchool getSchool() {
        return school;
    }

    public int getRank() {
        return rank;
    }

    public Record getRecord() {
        return record;
    }

    public Double getWeight() {
        return weight;
    }

    /*Adder Methods*/
    public void addToTotalOffense(int offense) {
        this.totalOffense += offense;
    }

    public void addToTotalDefense(int defense) {
        this.totalDefense += defense;
    }

    public void addToPointsAllowed(int pointsAllowed) {
        this.pointsAllowed += pointsAllowed;
    }

    public void addToPointsFor(int pointsFor) {
        this.pointsFor += pointsFor;
    }


    /*
    Getter Methods- Method names are tightly coupled to Ranking Algo Strings
     */
    public int getWins() {
        return record.getWins();
    }

    public int getLosses() {
        return record.getLosses();
    }

    public double getPointsForPerGame() {
        return (double) pointsFor / gamesPlayed;
    }

    public double getPointsAllowedPerGame() {
        return (double) pointsAllowed / gamesPlayed;
    }

    public double getOffensePerGame() {
        return (double) totalOffense / gamesPlayed;
    }

    public double getDefensePerGame() {
        return (double) totalDefense / gamesPlayed;
    }

    public int getTotalOffense() {
        return totalOffense;
    }

    public int getTotalDefense() {
        return totalDefense;
    }

    public int getPointsAllowed() {
        return pointsAllowed;
    }

    public int getPointsFor() {
        return pointsFor;
    }

    public int getGamesPlayed() {
        boolean recordAndGamesPlayedAreEqual = gamesPlayed == (record.getWins() + record.getLosses());
        if (!recordAndGamesPlayedAreEqual) {
            throw new RuntimeException("Problem parsing games");
        }
        return gamesPlayed;
    }

    public void incGamesPlayed() {
        gamesPlayed++;
    }

    public List<SerializableGame> getSchedule() {
        return schedule;
    }

    public double getStrengthOfSchedulePerGame() {
        if (strengthOfSchedule == -1.0) {
            throw new IllegalStateException("Strength of Schedule has not been calculated");
        }
         return (double) strengthOfSchedule / getGamesPlayed();
    }

    public int getStrengthOfSchedule() {
        return strengthOfSchedule;

    }

    /*
    Setter Methods
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setStrengthOfSchedule(int strengthOfSchedule) {
        this.strengthOfSchedule = strengthOfSchedule;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    /*
    Nested Classes
     */
    public static class Record {
        private int wins;
        private int losses;

        Record() {
            wins = 0;
            losses = 0;
        }

        public Record(int wins, int loses) {
            this.wins = wins;
            this.losses = loses;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public void incWins() {
            wins++;
        }

        public void incLoses() {
            losses++;
        }
    }
}
