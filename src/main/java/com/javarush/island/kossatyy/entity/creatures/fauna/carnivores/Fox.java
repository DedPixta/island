package com.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
@Getter
public class Fox extends Carnivore {

    private static final AtomicLong idCounter = new AtomicLong(0);

    private static final int groupID = 2;

    private final long id = idCounter.getAndIncrement();
    public Fox() {
        super("\uD83E\uDD8A", groupID, 8, 2);
    }

    @Override
    public Creature spawn() {
        return new Fox();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fox fox = (Fox) o;
        return id == fox.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, id);
    }
}
