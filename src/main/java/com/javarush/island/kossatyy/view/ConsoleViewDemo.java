package com.javarush.island.kossatyy.view;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.map.Cell;
import com.javarush.island.kossatyy.entity.map.Island;
import com.javarush.island.kossatyy.settings.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleViewDemo implements View{

    public static class Color {


        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";

        public static final String FILL_RED = "\u001B[41m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
        public static final String FILL_BLUE = "\u001B[44m";
        public static final String FILL_PURPLE = "\u001B[45m";
        public static final String FILL_CYAN = "\u001B[46m";
        public static final String FILL_WHITE = "\u001B[47m";

        private static final String[] Scale = {
                FILL_GREEN, FILL_BLUE, FILL_YELLOW, FILL_PURPLE, FILL_WHITE,
                GREEN, BLUE, YELLOW, PURPLE, RED,
        };


        public static String getColor(int size, int maxCount) {
            if (size > maxCount) {
                return Color.FILL_RED;
            }
            int index = Scale.length - 1 - Scale.length * size / (maxCount + 1);
            return Scale[index];
        }
    }

    private final Island island;
    private final int cellWidth = 3;
    private final String border = "═".repeat(cellWidth);

    public ConsoleViewDemo(Island island) {
        this.island = island;
    }


    @Override
    public String showStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        Cell[][] cells = island.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                var residents = cell.getResidents();
                if (Objects.nonNull(residents)) {
                    residents.values().stream()
                            .filter(set -> set.size() > 0)
                            .forEach(set -> {
                                        String name = set.stream().findAny().get().getClass().getSimpleName();
                                        String icon = set.stream().findAny().get().getIcon() + name;
                                        statistics.put(icon, statistics.getOrDefault(icon, 0) + set.size());
                                    }
                            );
                }
            }
        }
        System.out.println(statistics + "\n");
        return statistics.toString();
    }

    @Override
    public String showMap() {
        StringBuilder out = new StringBuilder("\n");
        Cell[][] cells = island.getCells();
        final int cols = Config.getConfig().getColumns();
        final int rows = Config.getConfig().getRows();
        for (int row = 0; row < rows; row++) {
            out.append(row == 0
                    ? line(cols, '╔', '╦', '╗')
                    : line(cols, '╠', '╬', '╣')
            ).append("\n");
            for (int col = 0; col < cols; col++) {
                String residentSting = get(cells[row][col]);
                out.append(String.format("║%-" + cellWidth + "s", residentSting));
            }
            out.append('║').append("\n");
        }
        out.append(line(cols, '╚', '╩', '╝'));
        System.out.println(out);
        return out.toString();
    }

    private String get(Cell cell) {
        cell.getLock().lock();
        String collect = cell.getResidents().values().stream()
                .filter((list) -> list.size() > 0)
                .sorted((o1, o2) -> o2.size() - o1.size())
                .limit(cellWidth)
                .map(list -> {
                    Creature creature = list.stream().findAny().get();
                    int maxCount = Config.getConfig().getLimitOfCreatures().get(creature.getClass().getSimpleName());
                    String color = Color.getColor(list.size(), maxCount);
                    return color + creature.getLetter() + Color.RESET;
                })
                .map(Object::toString)
                .collect(Collectors.joining());
        long count = cell.getResidents().values().stream()
                .filter((list) -> list.size() > 0).limit(cellWidth).count();
        String blank = count < cellWidth ? ".".repeat((int) (cellWidth - count)) : "";
        cell.getLock().unlock();
        return collect + blank;
    }

    private String line(int cols, char left, char center, char right) {
        return (IntStream.range(0, cols)
                .mapToObj(col -> (col == 0 ? left : center) + border)
                .collect(Collectors.joining("", "", String.valueOf(right))));
    }
}
