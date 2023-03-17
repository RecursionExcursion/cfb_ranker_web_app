package com.foofinc.cfb_ranker.service.entity;

public class StatWeight {

    private static StatWeight INSTANCE;

    private int winWeight = 6;
    private int lossWeight = 6;

    private int pointsForWeight = 4;
    private int pointsAllowedWeight = 4;

    private int offensiveYardsWeight = 1;
    private int defensiveYardsWeight = 1;

    private int strengthOfScheduleWeight = 2;
    private int pollInertiaWeight = 5;

    private StatWeight() {
    }

    public static StatWeight getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatWeight();
        }
        return INSTANCE;
    }

    public void setWeights(int newWinWeight, int newLossWeight,
                                  int newPointsForWeight, int newPointsAllowedWeight,
                                  int newOffensiveYardsWeight, int newDefensiveYardsWeight,
                                  int newStrengthOfScheduleWeight, int newPollInertiaWeight) {
        winWeight = newWinWeight;
        lossWeight = newLossWeight;
        pointsForWeight = newPointsForWeight;
        pointsAllowedWeight = newPointsAllowedWeight;
        offensiveYardsWeight = newOffensiveYardsWeight;
        defensiveYardsWeight = newDefensiveYardsWeight;
        strengthOfScheduleWeight = newStrengthOfScheduleWeight;
        pollInertiaWeight = newPollInertiaWeight;
    }

    public int getWinWeight() {
        return winWeight;
    }

    public int getLossWeight() {
        return lossWeight;
    }

    public int getPointsForWeight() {
        return pointsForWeight;
    }

    public int getPointsAllowedWeight() {
        return pointsAllowedWeight;
    }

    public int getOffensiveYardsWeight() {
        return offensiveYardsWeight;
    }

    public int getDefensiveYardsWeight() {
        return defensiveYardsWeight;
    }

    public int getStrengthOfScheduleWeight() {
        return strengthOfScheduleWeight;
    }

    public int getPollInertiaWeight() {
        return pollInertiaWeight;
    }
}
