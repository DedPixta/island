package com.javarush.island.kossatyy.services;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.creatures.fauna.Animal;
import com.javarush.island.kossatyy.entity.map.Cell;
import com.javarush.island.kossatyy.entity.map.Island;
import com.javarush.island.kossatyy.exceptions.CreatureOperationFail;
import com.javarush.island.kossatyy.settings.Config;
import com.javarush.island.kossatyy.util.Satiety;

import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Phaser;


public class CreatureWorker implements Runnable {

    private final Creature creature;
    private final Island island;
    private final Phaser phaser;
    private final Queue<Task> tasksEat = new ConcurrentLinkedQueue<>();
    private final Queue<Task> tasksReproduce = new ConcurrentLinkedQueue<>();
    private final Queue<Task> tasksMove = new ConcurrentLinkedQueue<>();
    private final Queue<Task> tasksRefreshWeight = new ConcurrentLinkedQueue<>();
    private final Queue<Task> tasksStarved = new ConcurrentLinkedQueue<>();


    public CreatureWorker(Creature creature, Island island, Phaser phaser) {
        this.creature = creature;
        this.island = island;
        this.phaser = phaser;
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
        int groupID = creature.getGroupID();
        Set<Creature> creatures = cell.getResidents().get(groupID);
        if (Objects.nonNull(creatures)) {
            cell.getLock().lock();
            try {
                creatures.forEach(creature -> {
                    Task task;
                    if (creature instanceof Animal) {
                        Animal animal = (Animal) creature;

                        /*---------TEST------------------*/
//                        task = new Task(animal, o -> {
//                            Animal animalInCons = (Animal) o;
////                                    animalInCons.reproduce(cell);
////                                    animalInCons.eat(cell);
////                                    animalInCons.move(cell);
//                        });
//                        tasksEat.add(task);
                        /*---------TEST------------------*/


                        switch (animal.getSatiety()) { // TODO подумать как уменьшить
                            case WELL_FED:
                                task = new Task(animal, o -> {
                                    o.reproduce(cell);
                                });
                                tasksReproduce.add(task);
                                break;
                            case ALL_RIGHT:
                                task = new Task(animal, o -> {
                                    Animal animalInCons = (Animal) o;
                                    animalInCons.move(cell);
                                });
                                tasksMove.add(task);
                                break;
                            case HUNGRY:
                                Map<Integer, Integer> myRation = Config.getConfig().getGroupFoodMap(groupID);
                                Map<Integer, Set<Creature>> residents = cell.getResidents();
                                boolean haveFoodHere = animal.findSomeFood(myRation, residents);
                                if (haveFoodHere) {
                                    task = new Task(animal, o -> {
                                        Animal animalInCons = (Animal) o;
                                        animalInCons.eat(cell);
                                    });
                                    tasksEat.add(task);
                                } else {
                                    task = new Task(animal, o -> {
                                        Animal animalInCons = (Animal) o;
                                        animalInCons.move(cell);
                                    });
                                    tasksMove.add(task);
                                }
                                break;
                            case WILL_BE_FINE:
                                task = new Task(animal, o -> {
                                    if (o.isAlive()) {
                                        o.setAlive(false);
                                    }
                                });
                                tasksStarved.add(task);
                                break;
                        }
                    } else {
                        task = new Task(creature, o -> {
                            o.reproduce(cell);
                        });
                        tasksReproduce.add(task);
                    }
                    Task refreshWeight = new Task(creature, o -> {
                        double curWeight = o.getCurWeight();
                        double maxWeight = o.getMaxWeight();
                        double weightNextDay = curWeight - maxWeight * 0.05;
                        double ratio = weightNextDay / maxWeight;
                        if (o instanceof Animal) {
                            Animal animal = (Animal) o;
                            Satiety satiety = animal.getSatiety();

                            if (ratio >= 0.9 && satiety != Satiety.WELL_FED) {
                                animal.setSatiety(Satiety.WELL_FED);
                            } else if (ratio >= 0.7 && ratio < 0.9 && satiety != Satiety.ALL_RIGHT) {
                                animal.setSatiety(Satiety.ALL_RIGHT);
                            } else if (ratio >= 0.5 && ratio < 0.7 && satiety != Satiety.HUNGRY) {
                                animal.setSatiety(Satiety.HUNGRY);
                            } else if (ratio < 0.5 && satiety != Satiety.WILL_BE_FINE) {
                                animal.setSatiety(Satiety.WILL_BE_FINE);
                            }
                        }
                        if (ratio < 0.1) {
                            o.deleteMe(cell);
                        } else {
                            o.setCurWeight(weightNextDay);
                        }
                    });
                    tasksRefreshWeight.add(refreshWeight);

                });
            } finally {
                cell.getLock().unlock();
            }
//            phaser.register();
            tasksEat.forEach(Task::run);
            tasksEat.clear();
//            phaser.arriveAndAwaitAdvance();

            tasksReproduce.forEach(Task::run);
            tasksReproduce.clear();
//            phaser.arriveAndAwaitAdvance();
//
            tasksMove.forEach(Task::run);
            tasksMove.clear();
//            phaser.arriveAndAwaitAdvance();

            tasksStarved.forEach(Task::run);
            tasksStarved.clear();
//            phaser.arriveAndAwaitAdvance();

            tasksRefreshWeight.forEach(Task::run);
            tasksRefreshWeight.clear();
//            phaser.arriveAndDeregister();
        }
    }
}
