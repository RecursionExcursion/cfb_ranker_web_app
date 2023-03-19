package com.foofinc.cfb_ranker.service.entity;

public class StatWeight {

    private int winWeight;
    private int lossWeight;

    private int pointsForWeight;
    private int pointsAllowedWeight;

    private int offensiveYardsWeight;
    private int defensiveYardsWeight;

    private int strengthOfScheduleWeight;
    private int pollInertiaWeight;

    public StatWeight(int newWinWeight, int newLossWeight,
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

    public void setWinWeight(int winWeight) {
        this.winWeight = winWeight;
    }

    public void setLossWeight(int lossWeight) {
        this.lossWeight = lossWeight;
    }

    public void setPointsForWeight(int pointsForWeight) {
        this.pointsForWeight = pointsForWeight;
    }

    public void setPointsAllowedWeight(int pointsAllowedWeight) {
        this.pointsAllowedWeight = pointsAllowedWeight;
    }

    public void setOffensiveYardsWeight(int offensiveYardsWeight) {
        this.offensiveYardsWeight = offensiveYardsWeight;
    }

    public void setDefensiveYardsWeight(int defensiveYardsWeight) {
        this.defensiveYardsWeight = defensiveYardsWeight;
    }

    public void setStrengthOfScheduleWeight(int strengthOfScheduleWeight) {
        this.strengthOfScheduleWeight = strengthOfScheduleWeight;
    }

    public void setPollInertiaWeight(int pollInertiaWeight) {
        this.pollInertiaWeight = pollInertiaWeight;
    }
}
