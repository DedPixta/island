package com.javarush.island.kossatyy.entity.creatures.fauna;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.map.Cell;
import com.javarush.island.kossatyy.exceptions.CreatureNotFound;
import com.javarush.island.kossatyy.interfaces.Eat;
import com.javarush.island.kossatyy.interfaces.Movable;
import com.javarush.island.kossatyy.settings.Config;
import com.javarush.island.kossatyy.util.Randomizer;
import com.javarush.island.kossatyy.util.Satiety;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Animal extends Creature implements Movable, Eat {


    private final int speed;

    public void setSatiety(Satiety satiety) {
//        System.out.println(this + "_" + this.satiety + " Satiety changed to " + satiety);  //TODO clear
        this.satiety = satiety;
    }

    private Satiety satiety;


    public Animal(String icon, int groupID, double maxWeight, int speed) {
        super(icon, groupID, maxWeight, true);
        this.speed = speed;
        this.satiety = Satiety.ALL_RIGHT;
    }

    @Override
    public void eat(Cell cell) {
//        System.out.printf("%s started eat\n", this); //TODO clear
        cell.getLock().lock();
        try {
            int myGroupID = this.getGroupID();
            Map<Integer, Integer> myRation = Config.getConfig().getGroupFoodMap(myGroupID);
            double curWeight = getCurWeight();
            double deltaWeight = getMaxWeight() - curWeight;

            Map<Integer, Set<Creature>> residents = cell.getResidents();
            Creature myTarget = getTarget(myRation, residents);
//        System.out.printf("%s chose target %s\n", this, myTarget); //TODO clear
            if (myTarget.isAlive()) {
                int chanceToKill = myRation.get(myTarget.getGroupID());
                int myTry = Randomizer.random(0, 100);
                if (myTry <= chanceToKill) {
                    myTarget.setAlive(false); //TODO нужен lock?
                } else return;
            }
            double curTargetWeight = myTarget.getCurWeight();
            double myFinalWeight;
            if (deltaWeight > curTargetWeight) {
                myFinalWeight = curWeight + curTargetWeight;
                myTarget.setCurWeight(0);
            } else {
                myFinalWeight = curWeight + deltaWeight;
                myTarget.setCurWeight(curTargetWeight - deltaWeight);
            }
            setCurWeight(myFinalWeight); //TODO нужен lock?
        } finally {
            cell.getLock().unlock();
        }
    }

    private Creature getTarget(Map<Integer, Integer> ration, Map<Integer, Set<Creature>> residents) {
        return residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .filter(resident -> ration.containsKey(resident.getKey()))
                .max(Comparator.comparingInt(resident -> ration.get(resident.getKey())))
                .orElseThrow(() -> new CreatureNotFound("target not found"))//TODO прочитать тему Stream, проверить правильность написания, понять как вставить причину
                .getValue()
                .iterator()
                .next();
    }

    public boolean findSomeFood(Map<Integer, Integer> ration, Map<Integer, Set<Creature>> residents) {
        return residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .map(Map.Entry::getKey)
                .anyMatch(ration::containsKey);
    }

    @Override
    public void move(Cell cell) {

//        System.out.printf("%s start move", this); //TODO clear

        if (speed > 0) {
            int numberOfSteps = Randomizer.random(speed);
            Set<Cell> visitedCells = new HashSet<>();
            Cell destination = cell;

            while (numberOfSteps > 0) {
                visitedCells.add(destination);
                List<Cell> neighbours = destination
                        .getNeighbours()
                        .stream()
                        .filter(o -> !visitedCells.contains(o))
                        .collect(Collectors.toList());
                int directions = neighbours.size();
                if (directions > 0) {
                    int myDirection = Randomizer.random(0, directions);//chooseDirect
                    destination = neighbours.get(myDirection);
                }
                numberOfSteps--;
            }
            if(moveTo(destination)){
                remove(cell);
            }
//            System.out.printf("%s finished move", this); //TODO clear
        }
    }

    private void remove(Cell cell) {
        cell.getLock().lock();
        try {
            cell.getResidents()
                    .get(getGroupID())
                    .remove(this);
        }finally {
            cell.getLock().unlock();
        }
    }

    private boolean moveTo(Cell destination) {
        destination.getLock().lock();
        try {
            Set<Creature> residents = destination.getResidents().get(getGroupID());
            int size = residents.size();
            int maxCount = Config.getConfig().getLimitOfCreatures().get(getClass().getSimpleName());
            if (size < maxCount){
                return residents.add(this);
            } else return false;
        } finally {
            destination.getLock().unlock();
        }

    }

}



