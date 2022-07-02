package ru.javarush.island.kossatyy.repository.factory;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.repository.Limit;
import ru.javarush.island.kossatyy.repository.maps.Ration;
import ru.javarush.island.kossatyy.setting.Config;
import ru.javarush.island.kossatyy.util.Satiety;

import java.util.Map;


public abstract class CreatureFactory implements Factory {
    protected int groupId;
    protected String type;
    protected Config config = Config.getConfig();
    protected boolean isAlive = true;
    protected double curWeight;
    protected Satiety satiety = Satiety.ALL_RIGHT;
    protected Ration ration;
    protected Limit limit;
    protected String icon;

    @Override
    public Map<String, Creature> getPrototypes() {
        return null;
    }
}
