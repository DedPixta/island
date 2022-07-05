package ru.javarush.island.kossatyy.services.task;

import lombok.RequiredArgsConstructor;
import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.map.Cell;

@RequiredArgsConstructor
public abstract class Task implements Runnable {

    protected final Creature creature;
    protected final Cell cell;

    public abstract void run();

}
