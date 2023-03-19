package com.foofinc.cfb_ranker.repository.persistence;

import com.foofinc.cfb_ranker.repository.model.SerializableSeason;

/**
 * Manager of ObjectSerializer
 * 'SerializablePlaceHolder' type should be replaced by the Object to be Serialized that implements the Serializable interface
 * FilePath and FileName must be updated
 * All are marked with //TODO
 * <p>
 * Handling multiple ObjectSerializer's are possible
 */


public enum SerializationManager {

    INSTANCE;

    private final String pathToFileFolder = "src/main/java/com/foofinc/cfb_ranker/repository/persistence/fbs_data/";
    private final String fileName = "team_data";
    private final ObjectSerializer<SerializableSeason> seasonSerializer = new ObjectSerializer<>(pathToFileFolder + fileName);

    public SerializableSeason loadSeason() {
        try {
            return seasonSerializer.load();
        } catch (Exception e) {
            seasonSerializer.save(new SerializableSeason());
            return seasonSerializer.load();
        }
    }

    public void saveSeason(SerializableSeason obj) {
        seasonSerializer.save(obj);
    }

    public boolean fileExists() {
        return seasonSerializer.fileExists();
    }

    public boolean dataIsCorrupted() {
        SerializableSeason season = seasonSerializer.load();
        if (season.getSchools().isEmpty() || season.getGames().isEmpty()) {
            return true;
        }
        if (season.getSchools().get(0).getSchool() == null || season.getGames().get(0).getFixture() == null) {
            return true;
        }
        return false;
    }
}
