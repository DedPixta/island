package com.island.services;

import com.island.entity.creatures.Creature;
import com.island.entity.creatures.fauna.Animal;
import com.island.entity.map.Cell;
import com.island.entity.map.Island;
import com.island.exception.CreatureOperationFail;
import com.island.repository.maps.Ration;
import com.island.repository.maps.Residents;
import com.island.services.task.*;

import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class CreatureWorker implements Runnable {
    private final Creature creature;
    private final Island island;
    private final Queue<Task> tasks = new ConcurrentLinkedQueue<>();
    private final Queue<Task> starvTasks = new ConcurrentLinkedQueue<>();
    private final CountDownLatch latch;

    public CreatureWorker(Creature creature, Island island, CountDownLatch latch) {
        this.creature = creature;
        this.island = island;
        this.latch = latch;
    }

    @Override
    public void run() {
        Cell[][] cells = island.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                try {
                    processOneCell(cell);
                } catch (Exception e) {
                    throw new CreatureOperationFail("Problem operation with creature - " + this.creature, e);
                }
            }
        }
    }

    private void processOneCell(Cell cell) {
        String type = creature.getType();
        Residents residents = cell.getResidents();
        Set<Creature> creatures = residents.get(type);

        if (Objects.nonNull(creatures)) {
            cell.getLock().lock();
            try {
                for (Creature creature : creatures) {
                    Task task = chooseAction(creature, cell, residents);
                    tasks.add(task);
                    Task starvation = new StarvTask(creature,cell);
                    starvTasks.add(starvation);
                }
            } finally {
                cell.getLock().unlock();
            }
            tasks.forEach(Task::run);
            tasks.clear();
            latch.countDown();

            starvTasks.forEach(Task::run);
            starvTasks.clear();
            latch.countDown();
        }
    }

    private Task chooseAction(Creature creature, Cell cell, Residents residents){
        Task task = null;
        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            switch (creature.getSatiety()) {
                case WELL_FED:
                    task = new ReproduceTask(creature, cell);
                    break;
                case ALL_RIGHT:
                    task = new MoveTask(creature, cell);
                    break;
                case HUNGRY:
                    Ration myRation = creature.getRation();
                    boolean haveFoodHere = animal.findSomeFood(myRation, residents);
                    if (haveFoodHere) {
                        task = new EatTask(creature, cell);
                    } else {
                        task = new MoveTask(creature, cell);
                    }
                    break;
                case WILL_BE_FINE:
                    task = new DeathTask(creature, cell);
                    break;
            }
        } else {
            task = new ReproduceTask(creature, cell);
        }
        return task;
    }
}
