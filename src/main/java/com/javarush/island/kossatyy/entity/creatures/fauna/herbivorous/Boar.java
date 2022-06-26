package com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
public class Boar extends Herbivorous{

    private static final AtomicLong idCounter = new AtomicLong(0);
    private static final int groupID= 11;
    private final long id = idCounter.getAndIncrement();

    public Boar() {
        super("\uD83D\uDC17", groupID,400, 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boar boar = (Boar) o;
        return id == boar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID,id);
    }

    @Override
    public Creature spawn() {
        return new Boar();
    }

}
