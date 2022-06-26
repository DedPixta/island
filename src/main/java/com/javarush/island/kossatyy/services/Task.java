package com.javarush.island.kossatyy.services;

import com.javarush.island.kossatyy.entity.creatures.Creature;

import java.util.function.Consumer;

public class Task implements Runnable {


    private final Creature creature;
    private final Consumer<Creature> operation;

    public Task(Creature creature, Consumer<Creature> operation) {
        this.creature = creature;
        this.operation = operation;
    }

    public void run() {
        operation.accept(creature);
    }

}
