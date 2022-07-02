package ru.javarush.island.kossatyy.entity.creatures.flora;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.repository.CreatureInfo;
import ru.javarush.island.kossatyy.repository.Limit;

public abstract class Plant extends Creature {

    public Plant(CreatureInfo info, Limit limit) {
        super(info, limit);
    }

}
