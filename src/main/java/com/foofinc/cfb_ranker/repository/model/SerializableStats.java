package com.foofinc.cfb_ranker.repository.model;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractStats;

import java.io.Serializable;

public class SerializableStats extends AbstractStats implements Serializable {

    public SerializableStats(String category, String stat) {
        super(category, stat);
    }

    public SerializableStats(AbstractStats stats) {
        category = stats.getStat();
        stat = stats.getStat();
    }
}
