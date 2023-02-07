package com.foofinc.cfb_ranker.repository.model;

import java.io.Serializable;
import java.util.List;

/*
 *Top Level data structure of all School objects
 */

public class Schools implements Serializable {

    private List<School> schools;

    public Schools(List<School> schools) {
        this.schools = schools;
    }

    public List<School> getSchoolsAsList() {
        return schools;
    }

    public List<School> getSchoolsAsSchools() {
        return schools;
    }

    public void loadSchools(Schools s) {
        schools = s.getSchoolsAsSchools();
    }

    public void loadSchools(List<School> s) {
        schools = s;
    }

    public void addSchool(School school) {
        schools.add(school);
    }
}
