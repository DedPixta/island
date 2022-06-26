package com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
public class Caterpillar extends Herbivorous{

    private static final AtomicLong idCounter = new AtomicLong(0);
    private static final int groupID= 14;
    private final long id = idCounter.getAndIncrement();

    public Caterpillar() {
        super("\uD83D\uDC1B", groupID,0.01, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caterpillar caterpillar = (Caterpillar) o;
        return id == caterpillar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID,id);
    }

    @Override
    public Creature spawn() {
        return new Caterpillar();
    }

}
