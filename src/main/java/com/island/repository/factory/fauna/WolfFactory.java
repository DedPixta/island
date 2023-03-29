package com.island.repository.factory.fauna;

import com.island.entity.creatures.Creature;
import com.island.entity.creatures.Group;
import com.island.entity.creatures.fauna.carnivores.Wolf;
import com.island.repository.CreatureInfo;
import com.island.repository.factory.CreatureFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class WolfFactory extends CreatureFactory {

    private final static AtomicInteger ID = new AtomicInteger(0);

    public WolfFactory() {
        groupId = Group.WOLF.getGroupId();
        type = Group.WOLF.getType();
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

        return new Wolf(creatureInfo, limit);
    }
}
