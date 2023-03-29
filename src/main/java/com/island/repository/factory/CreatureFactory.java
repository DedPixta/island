package com.island.repository.factory;

import com.island.entity.creatures.Creature;
import com.island.repository.Limit;
import com.island.repository.maps.Ration;
import com.island.setting.Config;
import com.island.util.Satiety;


public abstract class CreatureFactory{
    protected int groupId;
    protected String type;
    protected Config config = Config.getConfig();
    protected boolean isAlive = true;
    protected double curWeight;
    protected Satiety satiety = Satiety.ALL_RIGHT;
    protected Ration ration;
    protected Limit limit;
    protected String icon;

    public abstract Creature create(String type);
}
