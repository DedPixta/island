package com.island.entity.creatures.flora;

import com.island.entity.creatures.Creature;
import com.island.repository.CreatureInfo;
import com.island.repository.Limit;

public abstract class Plant extends Creature {
    public Plant(CreatureInfo info, Limit limit) {
        super(info, limit);
    }
}
