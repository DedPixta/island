package com.island.entity.creatures;

import com.island.entity.map.Cell;
import com.island.interfaces.Reproducible;
import com.island.repository.CreatureInfo;
import com.island.repository.Limit;
import com.island.repository.factory.EntityFactory;
import com.island.repository.maps.Ration;
import com.island.repository.maps.Residents;
import com.island.util.Satiety;

import java.util.Objects;
import java.util.Set;

public abstract class Creature implements Reproducible{
    private final CreatureInfo info;
    private final Limit limit;

    public Creature(CreatureInfo info, Limit limit) {
        this.info = info;
        this.limit = limit;
    }

    public String getType() {
        return info.getType();
    }

    public int getGroupId() {
        return info.getGroupId();
    }

    public boolean isAlive() {
        return info.isAlive();
    }

    public double getCurWeight() {
        return info.getCurWeight();
    }

    public void setCurWeight(double curWeight) {
        info.setCurWeight(curWeight);
    }

    public String getIcon() {
        return info.getIcon();
    }

    public Satiety getSatiety() {
        return info.getSatiety();
    }

    public void setSatiety(Satiety satiety) {
        info.setSatiety(satiety);
    }

    public Ration getRation() {
        return info.getRation();
    }

    public double getMaxWeight() {
        return limit.getMaxWeight();
    }

    public int getMaxPopulation() {
        return limit.getMaxPopulation();
    }

    public int getSpeed() {
        return limit.getSpeed();
    }

    public void die() {
        info.setAlive(false);
    }

    public void reproduce(Cell cell) {
        cell.getLock().lock();
        try {
            Residents residents = cell.getResidents();
            String type = getType();
            Set<Creature> sameCreatures = residents.get(type);
            int maxLimit = getMaxPopulation();

            if (sameCreatures.contains(this)
                    && sameCreatures.size() >= 2
                    && sameCreatures.size() < maxLimit) {
                Creature creature = EntityFactory.getFactory().create(type);
                sameCreatures.add(creature);
            }
        } finally {
            cell.getLock().unlock();
        }
    }

    public void deleteMe(Cell cell) {
        cell.getLock().lock();
        try {
            String type = getType();
            cell.getResidents().get(type).remove(this);
        } finally {
            cell.getLock().unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return Objects.equals(info, creature.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }

}
