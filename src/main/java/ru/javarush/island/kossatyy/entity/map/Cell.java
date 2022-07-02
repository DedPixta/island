package ru.javarush.island.kossatyy.entity.map;

import lombok.Getter;
import ru.javarush.island.kossatyy.repository.maps.Residents;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {

    private final int row;
    private final int column;

    @Getter
    private List<Cell> directions;

    @Getter
    private final Residents residents;

    @Getter
    private final Lock lock;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.residents = new Residents();
        this.lock = new ReentrantLock(true);
    }

    public void setDirections(List<Cell> directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "Cell" +
                "[" + row + "]" +
                "[" + column + "]";
    }
}
