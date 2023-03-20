package com.foofinc.cfb_ranker.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StatWeight implements Serializable {
    @JsonProperty("winWeight")
    private int winWeight;
    @JsonProperty("lossWeight")
    private int lossWeight;

    @JsonProperty("pointsForWeight")
    private int pointsForWeight;
    @JsonProperty("pointsAllowedWeight")
    private int pointsAllowedWeight;

    @JsonProperty("offensiveYardsWeight")
    private int offensiveYardsWeight;
    @JsonProperty("defensiveYardsWeight")
    private int defensiveYardsWeight;

    @JsonProperty("strengthOfScheduleWeight")
    private int strengthOfScheduleWeight;
    @JsonProperty("pollInertiaWeight")
    private int pollInertiaWeight;

    public StatWeight() {
    }

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
