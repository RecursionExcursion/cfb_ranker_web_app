package com.foofinc.cfb_ranker.service;

import com.foofinc.cfb_ranker.service.entity.RankedSeason;
import com.foofinc.cfb_ranker.service.entity.StatWeight;

public class ResponseDto {

    private Object[] objects = new Object[2];

    public ResponseDto(StatWeight statWeight, RankedSeason rankedSeason) {
            objects[0]= statWeight;
            objects[1] = rankedSeason;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
