package com.foofinc.cfb_ranker.service.dto;

/*
 *Json package ready dto to be passed by the controller to the caller
 * DS is a 2d Array[x][y], where x is the week -1 and y is the ranking - 1 of the team held in the element
 */
public class RankingsDto {

    SchoolDto[][] weeklyRankings;

    public RankingsDto(int x, int y) {
        this.weeklyRankings = new SchoolDto[x][y];
    }

    public SchoolDto[][] getWeeklyRankings() {
        return weeklyRankings;
    }

    public void setWeeklyRankings(SchoolDto[][] weeklyRankings) {
        this.weeklyRankings = weeklyRankings;
    }

    static class SchoolDto {
        private String schoolName;
        private int rank;
        private double pointsForPerGame;
        private double pointsAllowedPerGame;
        private double offensePerGame;
        private double defensePerGame;
        private double strengthOfSchedulePerGame;
        private String record;
        private Double weight;

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getRecord() {
            return record;
        }

        public void setRecord(String record) {
            this.record = record;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public double getPointsForPerGame() {
            return pointsForPerGame;
        }

        public void setPointsForPerGame(double pointsForPerGame) {
            this.pointsForPerGame = pointsForPerGame;
        }

        public double getPointsAllowedPerGame() {
            return pointsAllowedPerGame;
        }

        public void setPointsAllowedPerGame(double pointsAllowedPerGame) {
            this.pointsAllowedPerGame = pointsAllowedPerGame;
        }

        public double getOffensePerGame() {
            return offensePerGame;
        }

        public void setOffensePerGame(double offensePerGame) {
            this.offensePerGame = offensePerGame;
        }

        public double getDefensePerGame() {
            return defensePerGame;
        }

        public void setDefensePerGame(double defensePerGame) {
            this.defensePerGame = defensePerGame;
        }

        public double getStrengthOfSchedulePerGame() {
            return strengthOfSchedulePerGame;
        }

        public void setStrengthOfSchedulePerGame(double strengthOfSchedulePerGame) {
            this.strengthOfSchedulePerGame = strengthOfSchedulePerGame;
        }
    }

}
