package com.foofinc.cfb_ranker.repository.controller;


import com.foofinc.cfb_ranker.repository.persistence.MemorySerializationManager;

import java.io.Serializable;

class LocalMemoryRepo<T extends Serializable> {

    private final String filePath = "src/main/java/com/foofinc/cfb_ranker/repository/persistence/fbs_data/team_data";
    private final MemorySerializationManager<T> memorySerializationManager = new MemorySerializationManager<>(filePath);


    LocalMemoryRepo() {
    }

    void save(T t) {
        memorySerializationManager.save(t);
    }

    T load() {
        return memorySerializationManager.load();
    }

    boolean fileExists() {
        return memorySerializationManager.fileExists();
    }
}
