package ru.javarush.island.kossatyy.services;

import ru.javarush.island.kossatyy.entity.Game;
import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.setting.Config;
import ru.javarush.island.kossatyy.view.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class GameWorker extends Thread {

    private final Game game;
    private final int period;

    public GameWorker(Game game) {
        this.game = game;
        this.period = Config.getConfig().getPeriod();
    }

    @Override
    public void run() {

        View view = game.getView();
        view.showMap();
        view.showStatistics();
        ScheduledExecutorService mainPool = Executors.newScheduledThreadPool(1);
        Map<String, Creature> prototypes = game.getEntityFactory().getPrototypes();
        CountDownLatch latch = new CountDownLatch(prototypes.size());

        List<CreatureWorker> workers = prototypes
                .values()
                .stream()
                .map(creature -> new CreatureWorker(creature, game.getIsland(),latch))
                .collect(Collectors.toList());

        mainPool.scheduleWithFixedDelay(() -> {
            ExecutorService servicePool = Executors.newFixedThreadPool(4);
            workers.forEach(servicePool::submit);
            servicePool.shutdown();
            await(view, servicePool);
        }, period, period, TimeUnit.MILLISECONDS);
    }

    private void await(View view, ExecutorService servicePool) {
        try {
            if (servicePool.awaitTermination(3, TimeUnit.SECONDS)) {
                view.showMap();
                view.showStatistics();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
