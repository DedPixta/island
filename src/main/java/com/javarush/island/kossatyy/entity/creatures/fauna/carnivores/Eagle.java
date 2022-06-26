package com.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
@Getter
public class Eagle extends Carnivore {

    private static final AtomicLong idCounter = new AtomicLong(0);

    private static final int groupID = 4;

    private final long id = idCounter.getAndIncrement();
    public Eagle() {
        super("\uD83E\uDD85", groupID, 500, 3);
    }

    @Override
    public Creature spawn() {
        return new Eagle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eagle eagle = (Eagle) o;
        return id == eagle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, id);
    }
}
