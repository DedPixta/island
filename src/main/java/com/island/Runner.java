package com.island;

import com.island.entity.Game;
import com.island.entity.map.Island;
import com.island.repository.MapCreator;
import com.island.repository.factory.EntityFactory;
import com.island.repository.factory.Factory;
import com.island.services.GameWorker;
import com.island.setting.Config;
import com.island.view.ConsoleView;
import com.island.view.View;

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
