package com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous;

import com.javarush.island.kossatyy.entity.creatures.fauna.Animal;

public abstract class Herbivorous extends Animal {

    public Herbivorous(String icon, int groupID, double maxWeight, int speed) {
        super(icon, groupID, maxWeight, speed);
    }
}
