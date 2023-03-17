package com.foofinc.cfb_ranker.repository.abstract_models;

import java.io.Serializable;

public abstract class AbstractSchool implements Serializable {

    public long id;
    public String school;
    public String mascot;
    public String abbreviation;
    public String color;
    public String altColor;
    public String[] logos;

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
