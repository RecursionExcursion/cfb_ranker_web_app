package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractSchool;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map from all schools JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolDto extends AbstractSchool implements DTO{ }
