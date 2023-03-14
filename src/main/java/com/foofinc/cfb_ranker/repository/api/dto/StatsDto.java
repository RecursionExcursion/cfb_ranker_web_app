package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractStats;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatsDto extends AbstractStats implements DTO { }
