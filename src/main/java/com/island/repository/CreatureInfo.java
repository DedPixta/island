package com.island.repository;

import com.island.repository.maps.Ration;
import com.island.util.Satiety;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class CreatureInfo {
    private String type;
    private int groupId;
    private int creatureId;
    private boolean isAlive;
    private double curWeight;
    private String icon;
    private Satiety satiety;
    private Ration ration;

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
