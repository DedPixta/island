package com.island.services.task;

import com.island.entity.creatures.Creature;
import com.island.entity.creatures.fauna.Animal;
import com.island.entity.map.Cell;

public class EatTask extends Task {

    public EatTask(Creature creature, Cell cell) {
        super(creature, cell);
    }

    @Override
    public void run() {
        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            animal.eat(cell);
        }
    }
}
