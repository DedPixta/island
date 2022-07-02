package ru.javarush.island.kossatyy.services.task;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.creatures.fauna.Animal;
import ru.javarush.island.kossatyy.entity.map.Cell;
import ru.javarush.island.kossatyy.setting.Config;
import ru.javarush.island.kossatyy.util.Satiety;

public class StarvTask extends Task {

    private static final double WELL_FED = 0.8; //TODO need different logic
    private static final double ALL_RIGHT = WELL_FED - 0.2;
    private static final double HUNGRY = ALL_RIGHT - 0.4;

    public StarvTask(Creature creature, Cell cell) {
        super(creature, cell);
    }

    @Override
    public void run() {
        double curWeight = creature.getCurWeight();
        double maxWeight = creature.getMaxWeight();
        Config config = Config.getConfig();
        double weightNextDay = curWeight - maxWeight * config.getWeightDecreaseFactor();
        double weightRatio = weightNextDay / maxWeight;
        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            Satiety satiety = animal.getSatiety();

            if (weightRatio >= WELL_FED && satiety != Satiety.WELL_FED) {
                animal.setSatiety(Satiety.WELL_FED);
            } else if (weightRatio >= ALL_RIGHT && weightRatio < WELL_FED && satiety != Satiety.ALL_RIGHT) {
                animal.setSatiety(Satiety.ALL_RIGHT);
            } else if (weightRatio >= HUNGRY && weightRatio < ALL_RIGHT && satiety != Satiety.HUNGRY) {
                animal.setSatiety(Satiety.HUNGRY);
            } else if (weightRatio < HUNGRY && satiety != Satiety.WILL_BE_FINE) {
                animal.setSatiety(Satiety.WILL_BE_FINE);
            }
        }
        double deathThreshold = config.getDeathThreshold();
        if (weightRatio < deathThreshold) {
            creature.deleteMe(cell);
        } else {
            creature.setCurWeight(weightNextDay);
        }
    }
}
