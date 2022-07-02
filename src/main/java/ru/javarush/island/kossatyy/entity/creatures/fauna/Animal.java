package ru.javarush.island.kossatyy.entity.creatures.fauna;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.map.Cell;
import ru.javarush.island.kossatyy.exception.CreatureNotFound;
import ru.javarush.island.kossatyy.interfaces.Eat;
import ru.javarush.island.kossatyy.interfaces.Movable;
import ru.javarush.island.kossatyy.repository.CreatureInfo;
import ru.javarush.island.kossatyy.repository.Limit;
import ru.javarush.island.kossatyy.repository.maps.Ration;
import ru.javarush.island.kossatyy.repository.maps.Residents;
import ru.javarush.island.kossatyy.util.Randomizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Animal extends Creature implements Movable, Eat {

    public Animal(CreatureInfo info, Limit limit) {
        super(info, limit);
    }

    @Override
    public void move(Cell curCell) {
        int speed = getSpeed();
        int countOfSteps = Randomizer.random(speed);

        Cell destination = findDestinationCell(countOfSteps, curCell);

        if (moveTo(destination)) {
            remove(curCell);
        }
    }

    @Override
    public void eat(Cell cell) {
        cell.getLock().lock();
        try {
            Ration myRation = getRation();
            double curWeight = getCurWeight();
            double deltaWeight = getMaxWeight() - curWeight;
            Residents residents = cell.getResidents();
            Creature myTarget = getTarget(myRation, residents);
            String targetType = myTarget.getType();

            if (myTarget.isAlive()) {
                int chanceToKill = myRation.get(targetType);
                int myTry = Randomizer.random(0, 100);
                if (myTry <= chanceToKill) {
                    myTarget.die();
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
            setCurWeight(myFinalWeight);
        } finally {
            cell.getLock().unlock();
        }
    }

    public boolean findSomeFood(Ration ration, Residents residents) {
        return residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .map(Map.Entry::getKey)
                .anyMatch(ration::containsKey);
    }

    private Creature getTarget(Ration ration, Residents residents) {

        Set<Creature> targetCreatures = residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .filter(resident -> ration.containsKey(resident.getKey()))
                .max(Comparator.comparingInt(resident -> ration.get(resident.getKey())))
                .orElseThrow(() -> new CreatureNotFound("target not found"))//TODO прочитать тему Stream, проверить правильность написания, понять как вставить причину
                .getValue();

        return targetCreatures
                .stream()
                .skip(Randomizer.random(0,targetCreatures.size()))
                .iterator()
                .next();
    }

    private List<Cell> getAvailableDirections(Set<Cell> visitedCells, Cell destination) {
        return destination
                .getDirections()
                .stream()
                .filter(o -> !visitedCells.contains(o))
                .collect(Collectors.toList());
    }

    private Cell findDestinationCell(int countOfSteps, Cell cell) {
        Set<Cell> visitedCells = new HashSet<>();
        Cell destination = cell;

        while (countOfSteps > 0) {
            visitedCells.add(destination);
            List<Cell> directions = getAvailableDirections(visitedCells, destination);
            int countOfDirections = directions.size();
            if (countOfDirections > 0) {
                int myDirection = Randomizer.random(0, countOfDirections);
                destination = directions.get(myDirection);
            }
            countOfSteps--;
        }
        return destination;
    }

    private boolean moveTo(Cell destination) {
        destination.getLock().lock();
        try {
            String type = getType();
            Residents residents = destination.getResidents();
            Set<Creature> sameCreatures = residents.get(type);
            int countOfSameCreatures = sameCreatures.size();
            int maxCount = getMaxPopulation();
            if (countOfSameCreatures < maxCount) {
                return sameCreatures.add(this);
            } else return false;
        } finally {
            destination.getLock().unlock();
        }
    }

    private void remove(Cell cell) {
        cell.getLock().lock();
        try {
            Residents residents = cell.getResidents();
            String type = getType();
            Set<Creature> sameCreatures = residents.get(type);
            if (imStillHere(cell)) {
                sameCreatures.remove(this);
            }
        } finally {
            cell.getLock().unlock();
        }
    }

    private boolean imStillHere(Cell cell) {
        Residents residents = cell.getResidents();
        String type = getType();
        Set<Creature> sameCreatures = residents.get(type);
        return sameCreatures.contains(this);
    }

}
