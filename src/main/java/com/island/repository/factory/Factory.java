package com.island.repository.factory;

import com.island.entity.creatures.Creature;

import java.util.Map;

public interface Factory {

    Creature create(String type);

    Map<String, Creature> getPrototypes();

}
