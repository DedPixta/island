package ru.javarush.island.kossatyy;

import ru.javarush.island.kossatyy.entity.Game;
import ru.javarush.island.kossatyy.entity.map.Island;
import ru.javarush.island.kossatyy.repository.MapCreator;
import ru.javarush.island.kossatyy.repository.factory.EntityFactory;
import ru.javarush.island.kossatyy.repository.factory.Factory;
import ru.javarush.island.kossatyy.services.GameWorker;
import ru.javarush.island.kossatyy.setting.Config;
import ru.javarush.island.kossatyy.view.ConsoleView;
import ru.javarush.island.kossatyy.view.View;

public class Runner {
    public static void main(String[] args) {

        Config config = Config.getConfig();
        Factory entityFactory = new EntityFactory();
        MapCreator mapCreator = new MapCreator(entityFactory);
        Island island = mapCreator.createIsland(config);
        View view = new ConsoleView(island, entityFactory);
        Game game = new Game(island, entityFactory, view);
        GameWorker gameWorker = new GameWorker(game);
        gameWorker.start();
    }

}
