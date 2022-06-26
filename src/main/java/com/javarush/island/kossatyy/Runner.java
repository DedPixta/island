package com.javarush.island.kossatyy;

import com.javarush.island.kossatyy.entity.Game;
import com.javarush.island.kossatyy.entity.map.Island;
import com.javarush.island.kossatyy.entity.repository.EntityFactory;
import com.javarush.island.kossatyy.entity.repository.Factory;
import com.javarush.island.kossatyy.services.GameWorker;
import com.javarush.island.kossatyy.settings.Config;
import com.javarush.island.kossatyy.view.ConsoleView;
import com.javarush.island.kossatyy.view.ConsoleViewDemo;
import com.javarush.island.kossatyy.view.View;


public class Runner {
    public static void main(String[] args) {
        Config config = Config.getConfig();

        Island island = new Island(config);

        island.initialize(); // TODO почитать тему дизайны проектирования (injection)

        View view=new ConsoleView(island);
        Factory entityFactory = new EntityFactory();
        Game game = new Game(island, entityFactory, view);
        GameWorker gameWorker = new GameWorker(game);
        gameWorker.start();


    }
}
