package ru.javarush.island.kossatyy.services.task;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.map.Cell;

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
