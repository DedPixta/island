package com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
public class Rabbit extends Herbivorous{

    private static final AtomicLong idCounter = new AtomicLong(0);
    private static final int groupID= 7;
    private final long id = idCounter.getAndIncrement();

    public Rabbit() {
        super("\uD83E\uDD8C", groupID,2, 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rabbit rabbit = (Rabbit) o;
        return id == rabbit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID,id);
    }

    @Override
    public Creature spawn() {
        return new Rabbit();
    }

}
