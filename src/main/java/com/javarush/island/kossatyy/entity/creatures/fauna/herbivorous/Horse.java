package com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
public class Horse extends Herbivorous{

    private static final AtomicLong idCounter = new AtomicLong(0);
    private static final int groupID= 5;
    private final long id = idCounter.getAndIncrement();

    public Horse() {
        super("\uD83D\uDC0E", groupID,400, 4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return id == horse.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID,id);
    }

    @Override
    public Creature spawn() {
        return new Horse();
    }

}
