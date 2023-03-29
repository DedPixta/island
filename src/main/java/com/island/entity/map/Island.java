package com.island.entity.map;

public class Island {
    private final Cell[][] cells;

    public Island(int rows, int columns) {
        this.cells = new Cell[rows][columns];
    }

    public Cell[][] getCells() {
        return cells;
    }
}
