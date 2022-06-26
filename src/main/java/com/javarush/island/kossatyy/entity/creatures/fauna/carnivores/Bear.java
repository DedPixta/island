package com.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
@Getter
public class Bear extends Carnivore {

    private static final AtomicLong idCounter = new AtomicLong(0);

    private static final int groupID = 3;

    private final long id = idCounter.getAndIncrement();
    public Bear() {
        super("\uD83D\uDC3B", groupID, 500, 2);
    }

    @Override
    public Creature spawn() {
        return new Bear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bear bear = (Bear) o;
        return id == bear.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, id);
    }
}
