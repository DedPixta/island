package com.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import com.javarush.island.kossatyy.entity.creatures.fauna.Animal;

public abstract class Carnivore extends Animal {

    public Carnivore(String icon, int groupID, double maxWeight, int speed) {
        super(icon, groupID, maxWeight, speed);
    }
}
