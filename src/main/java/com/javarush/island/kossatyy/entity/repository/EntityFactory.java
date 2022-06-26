package com.javarush.island.kossatyy.entity.repository;


import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.creatures.fauna.carnivores.*;
import com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous.*;
import com.javarush.island.kossatyy.entity.creatures.flora.Herb;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory implements Factory{

    private static final Map<Integer, Creature> ALFA_SQUAD = new HashMap<>(){{ //TODO подумать про автогенерацию
        put(0,new Wolf());
        put(1,new Snake());
        put(2,new Fox());
        put(3,new Bear());
        put(4,new Eagle());
        put(5,new Horse());
        put(6,new Deer());
        put(7,new Rabbit());
        put(8,new Mouse());
        put(9,new Goat());
        put(10,new Sheep());
        put(11,new Boar());
        put(12,new Buffalo());
        put(13,new Duck());
        put(14,new Caterpillar());
        put(15,new Herb());
    }};

    public Map<Integer, Creature> getAlfaSquad() {
        return ALFA_SQUAD;
    }

}
