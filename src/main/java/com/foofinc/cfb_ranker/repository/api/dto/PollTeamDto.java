package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractPollTeam;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PollTeamDto extends AbstractPollTeam implements DTO{ }
