package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractTeam;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto extends AbstractTeam implements DTO { }
