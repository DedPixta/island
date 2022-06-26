package com.javarush.island.kossatyy.view;

import com.javarush.island.kossatyy.entity.creatures.Creature;
import com.javarush.island.kossatyy.entity.creatures.fauna.carnivores.Carnivore;
import com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous.Herbivorous;
import com.javarush.island.kossatyy.entity.map.Cell;
import com.javarush.island.kossatyy.entity.map.Island;
import com.javarush.island.kossatyy.entity.repository.EntityFactory;
import com.javarush.island.kossatyy.settings.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConsoleView implements View {

    public static class Color {


        public static final String RESET = "\u001B[0m";

        public static final String FILL_RED = "\u001B[41m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
    }

    private final Island island;
    private int day = 0;

    public ConsoleView(Island island) {
        this.island = island;
    }


    @Override
    public String showStatistics() {
        Map<String, Integer> statisticCarni = new HashMap<>();
        Map<String, Integer> statisticHerbi = new HashMap<>();
        Map<String, Integer> statisticPlant = new HashMap<>();
        Cell[][] cells = island.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                var residents = cell.getResidents();
                if (Objects.nonNull(residents)) {
                    residents.values().stream()
                            .filter(set -> set.size() > 0)
                            .forEach(set -> {
                                        Creature creature = set.stream().findAny().get();
                                        String name = creature.getClass().getSimpleName();
                                        String icon = creature.getIcon();
                                        String info = icon + name;

                                        if (creature instanceof Herbivorous) {
                                            statisticHerbi.put(info, statisticHerbi.getOrDefault(info, 0) + set.size());
                                        } else if (creature instanceof Carnivore) {
                                            statisticCarni.put(info, statisticCarni.getOrDefault(info, 0) + set.size());
                                        } else {
                                            statisticPlant.put(info, statisticPlant.getOrDefault(info, 0) + set.size());
                                        }
                                    }
                            );
                }
            }
        }
        System.out.printf("--------Island DAY %d--------%n", day++);
        System.out.println("Carnivores:");
        statisticCarni.forEach((key, value) -> System.out.printf("%19s - %d%n", key, value));
        System.out.println("Herbivorous:");
        statisticHerbi.forEach((key, value) -> System.out.printf("%19s - %d%n", key, value));
        System.out.println("Plants:");
        statisticPlant.forEach((key, value) -> System.out.printf("%19s - %d%n", key, value));
        System.out.println("----------------------------");
        return statisticCarni.toString() + statisticHerbi.toString() + statisticPlant.toString();
    }

    @Override
    public String showMap() {
        StringBuilder out = new StringBuilder("\n");
        Map<String, Integer> maxPopulation = Config.getConfig().getMaxPopulation();
        Map<String, Integer> curPopulation = new HashMap<>();
        Map<Integer, Creature> creatures = new EntityFactory().getAlfaSquad(); //TODO решить проблему с new
        Cell[][] cells = island.getCells();
        final int creatureCount = creatures.size();
        final int rows = 10;

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                var residents = cell.getResidents();
                if (Objects.nonNull(residents)) {
                    residents.values().stream()
                            .filter(set -> set.size() > 0)
                            .forEach(set -> {
                                        Creature creature = set.stream().findAny().get();
                                        String name = creature.getClass().getSimpleName();
                                        curPopulation.put(name, curPopulation.getOrDefault(name, 0) + set.size());
                                    }
                            );
                }
            }
        }


        for (int row = 0; row < rows; row++) {
            int percent = 100;
            out.append(row == 0
                    ? String.format("%-3s %%|", percent)
                    : String.format(" %-2s %%|", percent - row * 10)
            );
            for (int col = 0; col < creatureCount; col++) {
                String residentString = fill(row, col, maxPopulation, curPopulation, creatures);
                int cellWidth = 5;
                out.append(String.format("%-" + cellWidth + "s", residentString));
            }
            out.append("\n");
        }
        out.append(" ".repeat(5)).append("| ");

        for (int i = 0, bound = 1; i < creatures.size(); i++) {
            String icon = creatures.get(i).getIcon();
            out.append(icon).append(" ".repeat(bound));

            if (i < 7 && i > 0) {
                if (i % 2 == 0) {
                    bound--;
                } else {
                    bound++;
                }
            } else if (i > 7) {
                if (i % 2 == 0) {
                    bound++;
                } else {
                    bound--;
                }
            }
        }

        System.out.println(out);
        return out.toString();
    }

    private String fill(int row, int col, Map<String, Integer> maxPopulation, Map<String, Integer> curPopulation, Map<Integer, Creature> creatures) {
        Creature curCreature = creatures.get(col);
        String name = curCreature.getClass().getSimpleName();
        int mapRow = Config.getConfig().getRows();
        int mapCol = Config.getConfig().getColumns();
        int maxCount = maxPopulation.get(name) * mapRow * mapCol;
        int curCount = curPopulation.get(name);
        double ratioPercent = 100.0 * curCount / maxCount;

        int curPercent = 100 - row * 10;
        String filler = ".";
        String color = Color.RESET;

        if (curPercent < ratioPercent) {
            if (ratioPercent >= 70) {
                color = Color.FILL_GREEN;
            } else if (ratioPercent >= 30 && ratioPercent < 70) {
                color = Color.FILL_YELLOW;
            } else {
                color = Color.FILL_RED;
            }
            filler = " ";
        }
        return "." + color + filler + Color.RESET + ".";
    }
}
