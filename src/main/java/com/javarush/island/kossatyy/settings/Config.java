package com.javarush.island.kossatyy.settings;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.repository.EntityFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public final class Config { // TODO понять что переносить из настроек в файл
    private static volatile Config config;

    private final int rows = 10; //TODO Перенести в файл настройки
    private final int columns = 20;

    private final int PERIOD = 1500;

    private final Map<Integer, Map<Integer, Integer>> foodMap;

    private final Map<String, Integer> maxPopulation = new HashMap<>(){{
        put("Wolf", 30);
        put("Snake", 30);
        put("Fox", 30);
        put("Bear", 5);
        put("Eagle", 20);
        put("Horse", 20);
        put("Deer", 20);
        put("Rabbit", 150);
        put("Mouse", 500);
        put("Goat", 140);
        put("Sheep", 140);
        put("Boar", 50);
        put("Buffalo", 10);
        put("Duck", 200);
        put("Caterpillar", 1000);
        put("Herb", 200);
    }};
    private final Map<String, Integer> limitOfCreatures = new HashMap<>() {
        {
            put("Wolf", 30);
            put("Snake", 30);
            put("Fox", 30);
            put("Bear", 5);
            put("Eagle", 20);
            put("Horse", 20);
            put("Deer", 20);
            put("Rabbit", 150);
            put("Mouse", 500);
            put("Goat", 140);
            put("Sheep", 140);
            put("Boar", 50);
            put("Buffalo", 10);
            put("Duck", 200);
            put("Caterpillar", 1000);
            put("Herb", 200);
        }
    };

    private final int[][] ration = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };



    private Config() {
        foodMap = createFoodMap(ration);
    }

    private Map<Integer, Map<Integer, Integer>> createFoodMap(int[][] ration) {
        Map<Integer, Map<Integer, Integer>> foodMap = new HashMap<>();
        Map<Integer, Creature> ALFA_SQUAD = new EntityFactory().getAlfaSquad(); //TODO inject?

        for (Creature creature : ALFA_SQUAD.values()) {
            int groupID = creature.getGroupID();
            Map<Integer, Integer> rationForGroup = new HashMap<>();
            for (int myFood = 0; myFood < ration[groupID].length; myFood++) {
                int chanceToEat = ration[groupID][myFood];
                rationForGroup.putIfAbsent(myFood, chanceToEat);
            }
            foodMap.putIfAbsent(groupID,rationForGroup);
        }
        return foodMap;
    }

    public static Config getConfig(){
        Config result = config;
        if(result != null){
            return result;
        }
        synchronized (Config.class){
            if (config == null){
                config = new Config();
            }
        }
        return config;
    }

    public int getPERIOD() {
        return PERIOD;
    }

    public Map<Integer, Map<Integer, Integer>> getWholeFoodMap() {
        return foodMap;
    }

    public Map<Integer, Integer> getGroupFoodMap(Integer groupID) {
        return Config.getConfig()
                .getWholeFoodMap()
                .get(groupID)
                .entrySet()
                .stream()
                .filter(myFood -> myFood.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }



    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Map<String, Integer> getLimitOfCreatures() {
        return limitOfCreatures;
    }

    public int[][] getRation() {
        return ration;
    }

    public Map<String, Integer> getMaxPopulation() {
        return maxPopulation;
    }

}
