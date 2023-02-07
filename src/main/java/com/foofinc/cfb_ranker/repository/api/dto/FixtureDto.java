package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map game from weekly games JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDto implements DTO {
    long id;
    private TeamDto[] teams;

    public FixtureDto(TeamDto[] teams) {
        this.teams = teams;
    }

    public FixtureDto() {
    }

    public TeamDto[] getTeams() {
        return teams;
    }

    public void setTeams(TeamDto[] teams) {
        this.teams = teams;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fixture{" +
                "teams=" + Arrays.toString(teams) +
                '}';
    }

    /*
     *Inner class representing the two teams
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TeamDto implements DTO {
        private String school;
        private int points;
        private StatsDto[] stats;

        public TeamDto() {
        }

        public TeamDto(String school, int points, StatsDto[] stats) {
            this.school = school;
            this.points = points;
            this.stats = stats;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public StatsDto[] getStats() {
            return stats;
        }

        public void setStats(StatsDto[] stats) {
            this.stats = stats;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "school='" + school + '\'' +
                    ", points=" + points +
                    ", stats=" + Arrays.toString(stats) +
                    '}';
        }

        /**
         * Inner class representing the stats of the two teams
         */

        public static class StatsDto implements DTO {
            private String category;
            private String stat;

            public StatsDto() {
            }

            public StatsDto(String category, String stat) {
                this.category = category;
                this.stat = stat;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getStat() {
                return stat;
            }

            public void setStat(String stat) {
                this.stat = stat;
            }

            @Override
            public String toString() {
                return "Stats{" +
                        "category='" + category + '\'' +
                        ", stat='" + stat + '\'' +
                        '}';
            }
        }
    }
}
