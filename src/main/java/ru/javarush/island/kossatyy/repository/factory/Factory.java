package ru.javarush.island.kossatyy.repository.factory;

import ru.javarush.island.kossatyy.entity.creatures.Creature;

import java.util.Map;

public interface Factory {

    Creature create(String type);

    Map<String, Creature> getPrototypes();

}
