package com.javarush.island.kossatyy.entity.map;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.repository.EntityFactory;
import com.javarush.island.kossatyy.settings.Config;
import com.javarush.island.kossatyy.util.Randomizer;

import java.util.*;

public class Island {

    private final Config config;
    private final Cell[][] cells;

    public Island(Config config) {
        this.config = config;
        this.cells = new Cell[config.getRows()][config.getColumns()];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void initialize() {

        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                cells[row][column] = new Cell(row, column, config);
            }
        }

        int rows = config.getRows();
        int columns = config.getColumns();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];

                populate(cell);

                List<Cell> neighbours = new ArrayList<>();
                if (row > 0) neighbours.add(cells[row - 1][col]);
                if (col > 0) neighbours.add(cells[row][col - 1]);
                if (row < rows - 1) neighbours.add(cells[row + 1][col]);
                if (col < columns - 1) neighbours.add(cells[row][col + 1]);

                cell.setNeighbours(neighbours);
            }
        }

    }

    public void populate(Cell cell) {
        Map<Integer, Creature> ALFA_SQUAD = new EntityFactory().getAlfaSquad(); //TODO inject?

        for (Creature creature : ALFA_SQUAD.values()) {

            int maxCount = config.getLimitOfCreatures().get(creature.getClass().getSimpleName());
            int count = Randomizer.random(maxCount);
            Set<Creature> creatures = new HashSet<>();

            for (int i = 0; i < count; i++) {
                creatures.add(creature.spawn());
            }

            cell.getResidents().put(creature.getGroupID(), creatures);
        }
    }

}
