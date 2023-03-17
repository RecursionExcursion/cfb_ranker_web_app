package com.foofinc.cfb_ranker.repository.model.new_models;

import com.foofinc.cfb_ranker.repository.abstract_models.AbstractFixture;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractGame;
import com.foofinc.cfb_ranker.repository.abstract_models.AbstractGameData;

import java.io.Serializable;

public class SerializableGame extends AbstractGame implements Serializable {

    public SerializableGame(long id, AbstractFixture fixture, AbstractGameData gameData) {
        super(id, fixture, gameData);
    }

    public SerializableGame(AbstractGame game) {
        id = game.getId();
        fixture = game.getFixture();
        gameData = game.getGameData();
    }
}
