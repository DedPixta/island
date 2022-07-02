package ru.javarush.island.kossatyy.services.task;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.creatures.fauna.Animal;
import ru.javarush.island.kossatyy.entity.map.Cell;

public class MoveTask extends Task{

    public MoveTask(Creature creature, Cell cell) {
        super(creature, cell);
    }

    @Override
    public void run() {
        if(creature instanceof Animal){
            Animal animal = (Animal) creature;
            int speed = animal.getSpeed();
            if (speed > 0) {
                animal.move(cell);
            }
        }
    }
}
