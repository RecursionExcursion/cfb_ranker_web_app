package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map from all schools JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolDto implements DTO{
    private long id;
    private String school;
    private String mascot;
    private String abbreviation;

    public String getSchool() {
        return school;
    }

    public SchoolDto() {
    }

    public SchoolDto(String school, String mascot, String abbreviation) {
        this.school = school;
        this.mascot = mascot;
        this.abbreviation = abbreviation;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String divider = "|";
        return school + divider + mascot + divider + abbreviation + divider;
    }
}
