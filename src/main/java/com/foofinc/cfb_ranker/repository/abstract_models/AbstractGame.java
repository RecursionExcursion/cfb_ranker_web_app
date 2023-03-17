package com.foofinc.cfb_ranker.repository.abstract_models;

import java.io.Serializable;

public class AbstractGame implements Serializable {
    protected long id;
    protected AbstractFixture fixture;
    protected AbstractGameData gameData;

    public AbstractGame() {
    }

    public AbstractGame(long id) {
        this.id = id;
    }

    public AbstractGame(long id, AbstractFixture fixture, AbstractGameData gameData) {
        this.id = id;
        this.fixture = fixture;
        this.gameData = gameData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AbstractFixture getFixture() {
        return fixture;
    }

    public void setFixture(AbstractFixture fixtureDto) {
        this.fixture = fixtureDto;
    }

    public AbstractGameData getGameData() {
        return gameData;
    }

    public void setGameData(AbstractGameData gameDataDto) {
        this.gameData = gameDataDto;
    }
}
