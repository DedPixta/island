package com.island.services.task;

import com.island.entity.creatures.Creature;
import com.island.entity.map.Cell;

public class DeathTask extends Task{

    public DeathTask(Creature creature, Cell cell) {
        super(creature, cell);
    }

    @Override
    public void run() {
        if(creature.isAlive()){
            creature.die();
        }
    }
}
