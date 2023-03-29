package com.island.repository.factory.fauna;

import com.island.entity.creatures.Creature;
import com.island.entity.creatures.Group;
import com.island.entity.creatures.fauna.herbivorous.Boar;
import com.island.repository.CreatureInfo;
import com.island.repository.factory.CreatureFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class BoarFactory extends CreatureFactory {

    private static final AtomicInteger ID = new AtomicInteger(0);

    public BoarFactory() {
        groupId = Group.BOAR.getGroupId();
        type = Group.BOAR.getType();
        limit = config.getLimit(type);
        curWeight = limit.getMaxWeight() * config.getStartWeightFactor();
        ration = config.getRation(type);
        icon = config.getIcon(type);
    }

    @Override
    public Creature create(String type) {

        int creatureId = ID.getAndIncrement();

        CreatureInfo creatureInfo = CreatureInfo.builder()
                .type(type)
                .groupId(groupId)
                .creatureId(creatureId)
                .isAlive(isAlive)
                .curWeight(curWeight)
                .icon(icon)
                .satiety(satiety)
                .ration(ration)
                .build();

        return new Boar(creatureInfo, limit);
    }
}
