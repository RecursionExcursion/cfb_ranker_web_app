package com.foofinc.cfb_ranker.repository.api.dto;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractGame;

/*
This class is a Data Structure used to hold data of multiple Jackson's objectmapper object.
POJO to hold games from weekly-games and team-games JSON
This class will be used to map this data to 'Game objects
 */
public class CompleteGameDto extends AbstractGame implements DTO { }
