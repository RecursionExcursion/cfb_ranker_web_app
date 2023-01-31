package com.foofinc.cfb_ranker.repository.controller;


import com.foofinc.cfb_ranker.repository.persistence.MemorySerializationManager;

import java.io.Serializable;

public class LocalMemoryController<T extends Serializable> {

    private final String filePath = "src/main/java/com/foofinc/cfb_ranker/repository/persistence/fbs_data/team_data";
    private final MemorySerializationManager<T> memorySerializationManager = new MemorySerializationManager<>(filePath);


    public LocalMemoryController() {
    }

    public void save(T t) {
        memorySerializationManager.save(t);
    }

    public T load() {
        return memorySerializationManager.load();
    }

    public boolean fileExists() {
       return memorySerializationManager.fileExists();
    }
}
