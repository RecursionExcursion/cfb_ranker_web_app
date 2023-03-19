package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractWeeklyPoll;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyPollDto extends AbstractWeeklyPoll implements DTO { }
