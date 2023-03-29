package com.island.entity.creatures.fauna.herbivorous;

import com.island.entity.creatures.fauna.Animal;
import com.island.repository.CreatureInfo;
import com.island.repository.Limit;

public abstract class Herbivorous extends Animal {
    public Herbivorous(CreatureInfo info, Limit limit) {
        super(info, limit);
    }
}
