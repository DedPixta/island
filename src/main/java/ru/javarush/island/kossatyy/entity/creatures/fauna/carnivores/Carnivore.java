package ru.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import ru.javarush.island.kossatyy.entity.creatures.fauna.Animal;
import ru.javarush.island.kossatyy.repository.CreatureInfo;
import ru.javarush.island.kossatyy.repository.Limit;

public abstract class Carnivore extends Animal {

    public Carnivore(CreatureInfo info, Limit limit) {
        super(info, limit);
    }
}
