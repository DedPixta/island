package com.javarush.island.kossatyy.entity.creatures.fauna.carnivores;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@ToString
@Getter
public class Wolf extends Carnivore {

    private static final AtomicLong idCounter = new AtomicLong(0);

    private static final int groupID = 0; //TODO дублируется с жестким конструктором, подумать как сделать общий параметр. Подтянуть из конфига

    private final long id = idCounter.getAndIncrement();
    public Wolf() {
        super("\uD83D\uDC3A", groupID, 50, 3);
    } //TODO from config

    @Override
    public Creature spawn() {
        return new Wolf();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wolf wolf = (Wolf) o;
        return id == wolf.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, id);
    } //TODO почитать можно ли Lombock Hashcode(groupID, id))
}
