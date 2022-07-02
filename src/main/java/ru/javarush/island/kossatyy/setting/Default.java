package ru.javarush.island.kossatyy.setting;

import ru.javarush.island.kossatyy.repository.Limit;

import java.util.Map;

import static java.util.Map.entry;

public final class Default {

    public static final int ROWS = 10;
    public static final int COLUMNS = 20;
    public static final int PERIOD = 500; // period for one cycle of day
    public static final double START_WEIGHT_FACTOR = 0.75; // amount of maxWeight on born creature
    public static final double WEIGHT_DECREASE_FACTOR = 0.02; // amount of maxWeight decreasing every day
    public static final double DEATH_THRESHOLD = 0.1; // ratio of creatures current weight with his maximum weight before delete

    /*
    int[K1][K2] = V ,where:
    K1 - Creature groupId;
    K2 - Target groupId;
    V - chance to eat.
    */
    public static final int[][] RATION_TABLE = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    /*
    Map<K,V> LIMITS,where:
    K - creature String type;
    V - limits of this creature Limit(double maxWeight, int maxPopulation, int speed).
    */
    public static final Map<String, Limit> LIMITS = Map.ofEntries( //TODO How to simple?
            entry("Wolf", new Limit(50, 30, 3)),
            entry("Snake", new Limit(15, 30, 1)),
            entry("Fox", new Limit(8, 30, 2)),
            entry("Bear", new Limit(500, 5, 2)),
            entry("Eagle", new Limit(6, 20, 3)),
            entry("Horse", new Limit(400, 20, 4)),
            entry("Deer", new Limit(300, 20, 4)),
            entry("Rabbit", new Limit(2, 150, 2)),
            entry("Mouse", new Limit(2, 500, 1)),
            entry("Goat", new Limit(60, 140, 3)),
            entry("Sheep", new Limit(70, 140, 3)),
            entry("Boar", new Limit(400, 5, 2)),
            entry("Buffalo", new Limit(700, 10, 3)),
            entry("Duck", new Limit(1, 200, 4)),
            entry("Caterpillar", new Limit(0.1, 1000, 0)),
            entry("Herb", new Limit(10, 200, 0)));

    /*
    Map<K,V> ICONS,where:
    K - creature String type;
    V - creature icon.
    */
    public static final Map<String, String> ICONS = Map.ofEntries(
            entry("Wolf", "\uD83D\uDC3A"),
            entry("Snake", "\uD83D\uDC0D"),
            entry("Fox", "\uD83E\uDD8A"),
            entry("Bear", "\uD83D\uDC3B"),
            entry("Eagle", "\uD83E\uDD85"),
            entry("Horse", "\uD83D\uDC34"),
            entry("Deer", "\uD83E\uDD8C"),
            entry("Rabbit", "\uD83D\uDC07"),
            entry("Mouse", "\uD83D\uDC01"),
            entry("Goat", "\uD83E\uDD8C"),
            entry("Sheep", "\uD83D\uDC11"),
            entry("Boar", "\uD83D\uDC17"),
            entry("Buffalo", "\uD83D\uDC03"),
            entry("Duck", "\uD83E\uDD86"),
            entry("Caterpillar", "\uD83D\uDC1B"),
            entry("Herb", "\u2618"));

}
