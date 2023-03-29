package com.island.services.task;

import com.island.entity.creatures.Creature;
import com.island.entity.map.Cell;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Task implements Runnable {

    protected final Creature creature;
    protected final Cell cell;

    public abstract void run();

}
