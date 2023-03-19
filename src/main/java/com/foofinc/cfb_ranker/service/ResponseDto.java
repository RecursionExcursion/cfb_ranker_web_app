package com.foofinc.cfb_ranker.service;

import com.foofinc.cfb_ranker.service.entity.RankedSeason;
import com.foofinc.cfb_ranker.service.entity.StatWeight;

public class ResponseDto {

    private Object[] responseObjects = new Object[2];

    public ResponseDto(StatWeight statWeight, RankedSeason rankedSeason) {
            responseObjects[0]= statWeight;
            responseObjects[1] = rankedSeason;
    }

    public Object[] getResponseObjects() {
        return responseObjects;
    }

    public void setResponseObjects(Object[] responseObjects) {
        this.responseObjects = responseObjects;
    }
}
