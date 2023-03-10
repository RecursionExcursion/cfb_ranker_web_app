package com.foofinc.cfb_ranker.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractGameData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDataDto extends AbstractGameData implements DTO { }
