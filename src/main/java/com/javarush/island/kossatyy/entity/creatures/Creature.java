package com.javarush.island.kossatyy.entity.creatures;

import com.javarush.island.kossatyy.entity.map.Cell;
import com.javarush.island.kossatyy.interfaces.Reproducible;
import com.javarush.island.kossatyy.interfaces.Spawn;
import com.javarush.island.kossatyy.settings.Config;
import com.javarush.island.kossatyy.util.Randomizer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class Creature implements Reproducible, Spawn {

    private final String icon;
    private final int groupID;
    private double maxWeight;
    private double curWeight;
    private boolean isAlive;


    public Creature(String icon, int groupID, double maxWeight, boolean isAlive) {
        this.icon = icon;
        this.groupID = groupID;
        this.maxWeight = maxWeight;
        this.curWeight = Randomizer.random(maxWeight/2, maxWeight);
        this.isAlive = isAlive;
    }


    private transient final String letter = getClass().getSimpleName().substring(0, 1); //TODO for test, delete
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void reproduce(Cell cell) {
        cell.getLock().lock();
        try {
            Set<Creature> creatures = cell.getResidents().get(getGroupID());
            int maxLimit = Config.getConfig().getLimitOfCreatures().getOrDefault(getClass().getSimpleName(), 10);
            if (creatures.contains(this)
                    && creatures.size() >= 2
                    && creatures.size() < maxLimit) {
                Creature creature = this.spawn();
                creatures.add(creature);
            }
        } finally {
            cell.getLock().unlock();
        }
    }
    public void deleteMe(Cell cell) {
        cell.getLock().lock();
        try {
            cell.getResidents().get(getGroupID()).remove(this);
        } finally {
            cell.getLock().unlock();
        }
    }

}
