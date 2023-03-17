package com.foofinc.cfb_ranker.repository.abstract_models;

import java.io.Serializable;

public abstract class AbstractStats implements Serializable {
    protected String category;
    protected String stat;

    public AbstractStats() {
    }

    public AbstractStats(String category, String stat) {
        this.category = category;
        this.stat = stat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "category='" + category + '\'' +
                ", stat='" + stat + '\'' +
                '}';
    }
}

