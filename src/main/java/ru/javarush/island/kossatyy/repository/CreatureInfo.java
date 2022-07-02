package ru.javarush.island.kossatyy.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.javarush.island.kossatyy.repository.maps.Ration;
import ru.javarush.island.kossatyy.util.Satiety;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class CreatureInfo {
    String type;
    int groupId;
    int creatureId;
    boolean isAlive;
    double curWeight;
    String icon;
    Satiety satiety;
    Ration ration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatureInfo that = (CreatureInfo) o;
        return groupId == that.groupId &&
                creatureId == that.creatureId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, creatureId);
    }

}
