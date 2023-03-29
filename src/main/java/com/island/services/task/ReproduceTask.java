package com.island.services.task;

import com.island.entity.creatures.Creature;
import com.island.entity.map.Cell;

public class ReproduceTask extends Task {

    public ReproduceTask(Creature creature, Cell cell) {
        super(creature, cell);
    }

    @Override
    public void run() {
        creature.reproduce(cell);
    }
}
