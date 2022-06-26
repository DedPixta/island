package com.javarush.island.kossatyy.entity.repository;

import com.javarush.island.kossatyy.entity.creatures.Creature;

import java.util.Map;

public interface Factory { // TODO почитать тему Фабрика

    Map<Integer, Creature> getAlfaSquad();

}
