package ru.javarush.island.kossatyy.services.task;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.map.Cell;

public class ReproduceTask extends Task {

    public ReproduceTask(Creature creature, Cell cell) {
        super(creature, cell);
    }

    @Override
    public void run() {
        creature.reproduce(cell);
    }
}
