package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractFixture;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map game from weekly games JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDto extends AbstractFixture implements DTO { }
