package ru.javarush.island.kossatyy.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.javarush.island.kossatyy.entity.creatures.Group;
import ru.javarush.island.kossatyy.repository.Limit;
import ru.javarush.island.kossatyy.repository.maps.FoodMap;
import ru.javarush.island.kossatyy.repository.maps.Ration;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PROTECTED)
public final class Config {

    public static final String SETTING_YAML = "kossatyy/setting.yaml";
    private static volatile Config CONFIG;

    private int rows;
    private int columns;
    private int period;
    private int[][] rationTable;
    private Map<String, Limit> limits;
    private Map<String, String> icons;
    private FoodMap foodMap;
    private double startWeightFactor;
    private double weightDecreaseFactor;
    private double deathThreshold;

    private Config() {
        loadDefaultSetting();
        updateFromFile();
    }

    public static Config getConfig() {
        Config result = CONFIG;
        if (result != null) {
            return result;
        }
        synchronized (Config.class) {
            if (CONFIG == null) {
                CONFIG = new Config();
            }
        }
        return CONFIG;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getPeriod() {
        return period;
    }

    public Limit getLimit(String type) {
        return limits.get(type);
    }

    public String getIcon(String type) {
        return icons.get(type);
    }

    public Ration getRation(String type) {
        return foodMap.get(type);
    }

    public double getStartWeightFactor() {
        return startWeightFactor;
    }

    public double getWeightDecreaseFactor() {
        return weightDecreaseFactor;
    }

    public double getDeathThreshold() {
        return deathThreshold;
    }

    private FoodMap createFoodMap(int[][] rationTable) {
        FoodMap foodMap = new FoodMap();
        Group[] groups = Group.values();
        int countGroups = groups.length;

        for (int myGroupId = 0; myGroupId < countGroups; myGroupId++) {
            Group group = groups[myGroupId];
            String type = group.getType();
            Ration ration = new Ration();

            for (int targetGroupId = 0; targetGroupId < countGroups; targetGroupId++) {
                Group targetGroup = groups[targetGroupId];
                String targetType = targetGroup.getType();
                int chanceToEat = rationTable[myGroupId][targetGroupId];
                if (chanceToEat > 0) {
                    ration.put(targetType, chanceToEat);
                }
            }
            foodMap.put(type, ration);
        }
        return foodMap;
    }

    private void loadDefaultSetting() {
        rows = Default.ROWS;
        columns = Default.COLUMNS;
        period = Default.PERIOD;
        rationTable = Default.RATION_TABLE;
        limits = Default.LIMITS;
        icons = Default.ICONS;
        startWeightFactor = Default.START_WEIGHT_FACTOR;
        weightDecreaseFactor = Default.WEIGHT_DECREASE_FACTOR;
        deathThreshold = Default.DEATH_THRESHOLD;

        foodMap = createFoodMap(rationTable);
    }

    @SneakyThrows
    private void updateFromFile() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectReader readerForUpdating = mapper.readerForUpdating(this);
        URL resource = Config.class.getClassLoader().getResource(SETTING_YAML);
        if (Objects.nonNull(resource)) {
            readerForUpdating.readValue(resource.openStream());
        }
    }

}
