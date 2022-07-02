package ru.javarush.island.kossatyy.repository;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.map.Cell;
import ru.javarush.island.kossatyy.entity.map.Island;
import ru.javarush.island.kossatyy.repository.factory.Factory;
import ru.javarush.island.kossatyy.repository.maps.Residents;
import ru.javarush.island.kossatyy.setting.Config;
import ru.javarush.island.kossatyy.util.Randomizer;

import java.util.*;

public class MapCreator {

    private final Factory entityFactory;
    private int rows;
    private int columns;

    public MapCreator(Factory factory) {
        this.entityFactory = factory;
    }

    public Island createIsland(Config config) {
        this.rows = config.getRows();
        this.columns = config.getColumns();

        Island island = new Island(rows, columns);

        createEmptyCells(island);
        populateIsland(island);
        findCellsNeighbors(island);

        return island;
    }

    private void createEmptyCells(Island island) {
        Cell[][] cells = island.getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    private void populateIsland(Island island) {
        Cell[][] cells = island.getCells();
        Map<String, Creature> prototypes = entityFactory.getPrototypes();

        for (Cell[] value : cells) {
            for (Cell cell : value) {
                Residents residents = cell.getResidents();
                for (Creature creature : prototypes.values()) {
                    String type = creature.getType();
                    int maxCount = creature.getMaxPopulation();
                    int count = Randomizer.random(maxCount/2,maxCount);
                    Set<Creature> creatures = new HashSet<>();

                    for (int i = 0; i < count; i++) {
                        creatures.add(entityFactory.create(type));
                    }
                    residents.put(type, creatures);
                }
            }
        }
    }

    private void findCellsNeighbors(Island island) {
        Cell[][] cells = island.getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                List<Cell> neighbours = findNeighbors(row, col, cells);
                cell.setDirections(neighbours);
            }
        }
    }

    private List<Cell> findNeighbors(int row, int col, Cell[][] cells) {
        List<Cell> result = new ArrayList<>();
        if (row > 0) result.add(cells[row - 1][col]);
        if (col > 0) result.add(cells[row][col - 1]);
        if (row < rows - 1) result.add(cells[row + 1][col]);
        if (col < columns - 1) result.add(cells[row][col + 1]);

        return result;
    }

}
