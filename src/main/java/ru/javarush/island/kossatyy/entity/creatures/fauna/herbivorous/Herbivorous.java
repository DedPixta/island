package ru.javarush.island.kossatyy.entity.creatures.fauna.herbivorous;

import ru.javarush.island.kossatyy.entity.creatures.fauna.Animal;
import ru.javarush.island.kossatyy.repository.CreatureInfo;
import ru.javarush.island.kossatyy.repository.Limit;

public abstract class Herbivorous extends Animal {

    public Herbivorous(CreatureInfo info, Limit limit) {
        super(info, limit);
    }
}
