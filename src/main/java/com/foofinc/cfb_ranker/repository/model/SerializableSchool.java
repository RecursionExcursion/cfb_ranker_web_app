package com.foofinc.cfb_ranker.repository.model;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractSchool;

import java.io.Serializable;

public class SerializableSchool extends AbstractSchool implements Serializable {

    public SerializableSchool(long id,
                              String school,
                              String mascot,
                              String abbreviation,
                              String color,
                              String altColor,
                              String[] logos) {
        super(id, school, mascot, abbreviation, color, altColor, logos);
    }

    public SerializableSchool(AbstractSchool school) {
        id = school.getId();
        this.school = school.getSchool();
        mascot = school.getMascot();
        abbreviation = school.getAbbreviation();
        color = school.getColor();
        altColor = school.getAltColor();
        logos = school.getLogos();
    }
}
