package ru.javarush.island.kossatyy.repository.factory.fauna;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.creatures.Group;
import ru.javarush.island.kossatyy.entity.creatures.fauna.carnivores.Eagle;
import ru.javarush.island.kossatyy.repository.CreatureInfo;
import ru.javarush.island.kossatyy.repository.factory.CreatureFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class EagleFactory extends CreatureFactory {

    private static final AtomicInteger ID = new AtomicInteger(0);

    public EagleFactory() {
        groupId = Group.EAGLE.getGroupId();
        type = Group.EAGLE.getType();
        limit = config.getLimit(type);
        curWeight = limit.getMaxWeight() * config.getStartWeightFactor();
        ration = config.getRation(type);
        icon = config.getIcon(type);
    }

    @Override
    public Creature create(String type) {

        int creatureId = ID.getAndIncrement();

        CreatureInfo creatureInfo = new CreatureInfo(type,
                groupId,
                creatureId,
                isAlive,
                curWeight,
                icon,
                satiety,
                ration);

        return new Eagle(creatureInfo, limit);
    }
}
