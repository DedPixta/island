package com.island.entity.creatures.fauna.carnivores;

import com.island.entity.creatures.fauna.Animal;
import com.island.repository.CreatureInfo;
import com.island.repository.Limit;

public abstract class Carnivore extends Animal {
    public Carnivore(CreatureInfo info, Limit limit) {
        super(info, limit);
    }
}
