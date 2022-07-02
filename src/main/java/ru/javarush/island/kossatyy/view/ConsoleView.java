package ru.javarush.island.kossatyy.view;

import ru.javarush.island.kossatyy.entity.creatures.Creature;
import ru.javarush.island.kossatyy.entity.creatures.fauna.carnivores.Carnivore;
import ru.javarush.island.kossatyy.entity.creatures.fauna.herbivorous.Herbivorous;
import ru.javarush.island.kossatyy.entity.map.Cell;
import ru.javarush.island.kossatyy.entity.map.Island;
import ru.javarush.island.kossatyy.repository.factory.Factory;
import ru.javarush.island.kossatyy.repository.maps.Residents;
import ru.javarush.island.kossatyy.setting.Config;

import java.util.*;

public class ConsoleView implements View {

    public static final int HEIGHT_DIAGRAM = 10;
    public static final int POPULATION_HIGH = 70; // int between 40 - 100
    public static final int POPULATION_AVERAGE = POPULATION_HIGH - 40;

    private final Island island;
    private final Factory factory;
    private int day = 0;

    public ConsoleView(Island island, Factory factory) {
        this.island = island;
        this.factory = factory;
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
        return printStatistics(statisticCarni, statisticHerbi, statisticPlant);
    }

    @Override
    public String showMap() {
        StringBuilder out = new StringBuilder("\n");
        Map<String, Creature> prototypes = factory.getPrototypes();
        List<Creature> creatures = new ArrayList<>(prototypes.values());
        Cell[][] cells = island.getCells();
        int creatureCount = creatures.size();
        Map<String, Integer> curPopulation = countCreatures(creatures, cells);
        drawDiagram(out, creatures, creatureCount, curPopulation);
        out.append(" ".repeat(5)).append("| ");
        drawIcons(out, creatures);
        System.out.println(out);
        return out.toString();
    }

    private String printStatistics(Map<String, Integer> statisticCarni, Map<String, Integer> statisticHerbi, Map<String, Integer> statisticPlant) {
        StringBuilder out = new StringBuilder("\n");
        out.append(String.format("--------Island DAY %d--------%n", day++));
        out.append("Carnivores:\n");
        out.append(MapToString(statisticCarni));
        out.append("Herbivorous:\n");
        out.append(MapToString(statisticHerbi));
        out.append("Plants:\n");
        out.append(MapToString(statisticPlant));
        out.append("----------------------------");

        System.out.println(out);
        return out.toString();
    }

    private String MapToString(Map<String, Integer> map) {
        StringBuilder out = new StringBuilder();
        map.forEach((key, value) -> out.append(String.format("%19s - %d%n", key, value)));
        return out.toString();
    }

    private void drawDiagram(StringBuilder out, List<Creature> creatures, int creatureCount, Map<String, Integer> curPopulation) {
        for (int row = 0; row < HEIGHT_DIAGRAM; row++) {
            int percent = 100;
            out.append(row == 0
                    ? String.format("%-3s %%|", percent)
                    : String.format(" %-2s %%|", percent - row * 10)
            );
            for (int col = 0; col < creatureCount; col++) {
                String residentString = fill(row, col, curPopulation, creatures);
                int cellWidth = 5;
                out.append(String.format("%-" + cellWidth + "s", residentString));
            }
            out.append("\n");
        }
    }

    private void drawIcons(StringBuilder out, List<Creature> creatures) {
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
    }

    private Map<String, Integer> countCreatures(List<Creature> creatures, Cell[][] cells) {
        Map<String, Integer> result = new HashMap<>();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                Residents residents = cell.getResidents();
                if (Objects.nonNull(residents)) {
                    creatures.forEach(creature -> {
                        String type = creature.getType();
                        result.put(type, result.getOrDefault(type, 0) + residents.get(type).size());
                    });
                }
            }
        }
        return result;
    }

    private String fill(int row, int col, Map<String, Integer> curPopulation, List<Creature> creatures) {
        Creature creature = creatures.get(col);
        String type = creature.getType();
        Config config = Config.getConfig();
        int mapRow = config.getRows();
        int mapCol = config.getColumns();
        int maxCount = creature.getMaxPopulation() * mapRow * mapCol;
        int curCount = curPopulation.get(type);
        double ratioPercent = 100.0 * curCount / maxCount;
        double curPercent = 100.0 - row * 10;

        String filler = choseFiller(ratioPercent, curPercent);
        return "." + filler + Color.RESET + ".";
    }

    private String choseFiller(double ratioPercent, double curPercent) {
        String filler = ".";
        String color = Color.RESET;

        if (curPercent <= ratioPercent) {
            if (ratioPercent >= POPULATION_HIGH) {
                color = Color.FILL_GREEN;
            } else if (ratioPercent >= POPULATION_AVERAGE && ratioPercent < POPULATION_HIGH) {
                color = Color.FILL_YELLOW;
            } else {
                color = Color.FILL_RED;
            }
            filler = " ";
        }
        return color + filler;
    }

    private static class Color {
        public static final String RESET = "\u001B[0m";
        public static final String FILL_RED = "\u001B[41m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
    }

}
