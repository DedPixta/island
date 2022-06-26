package com.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
@Getter
public class Snake extends Carnivore {

    private static final AtomicLong idCounter = new AtomicLong(0);

    private static final int groupID = 1;

    private final long id = idCounter.getAndIncrement();
    public Snake() {
        super("\uD83D\uDC0D", groupID, 15, 1);
    }

    @Override
    public Creature spawn() {
        return new Snake();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return id == snake.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, id);
    }
}
