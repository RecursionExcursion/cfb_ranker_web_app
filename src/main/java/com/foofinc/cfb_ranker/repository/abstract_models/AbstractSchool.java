package com.foofinc.cfb_ranker.repository.abstract_models;

public abstract class AbstractSchool {

    private long id;
    private String school;
    private String mascot;
    private String abbreviation;
    private String color;
    private String altColor;
    private String[] logos;

    public String getSchool() {
        return school;
    }

    public AbstractSchool() {
    }

    public AbstractSchool(long id,
                          String school,
                          String mascot,
                          String abbreviation,
                          String color,
                          String altColor,
                          String[] logos) {
        this.id = id;
        this.school = school;
        this.mascot = mascot;
        this.abbreviation = abbreviation;
        this.color = color;
        this.altColor = altColor;
        this.logos = logos;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAltColor() {
        return altColor;
    }

    public void setAltColor(String altColor) {
        this.altColor = altColor;
    }

    public String[] getLogos() {
        return logos;
    }

    public void setLogos(String[] logos) {
        this.logos = logos;
    }

    @Override
    public String toString() {
        String divider = "|";
        return school + divider + mascot + divider + abbreviation + divider;
    }
}
