package com.javarush.island.kossatyy.entity.map;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.repository.EntityFactory;
import com.javarush.island.kossatyy.settings.Config;
import com.javarush.island.kossatyy.util.Randomizer;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Getter
public class Cell {


    private final Lock lock = new ReentrantLock(true);
    private final int row;
    private final int column;

    private List<Cell> neighbours;

    private final Map<Integer, Set<Creature>> residents;

    private final Config config;

    public Cell(int row, int column, Config config) {
        this.residents = new HashMap<>();
        this.config = config;
        this.row = row;
        this.column = column;
    }

    public void setNeighbours(List<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public String toString() {
        return "Cell[" + row + "]" + "[" + column + "]";
    }
}
