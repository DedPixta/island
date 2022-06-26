package com.javarush.island.kossatyy.util;

/*----Unused----*/

public class ClassFinder{ // TODO почитать тему про ресурсы JAVA?
    public static final String[] searchPackages = {
            "com.javarush.island.kossatyy.entity.creatures.fauna.carnivores",
            "com.javarush.island.kossatyy.entity.creatures.fauna.herbivorous",
            "com.javarush.island.kossatyy.entity.creatures.flora"};

    public Class<?> findClassByName(String name) {

        String nameCapitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        for (String searchPackage : searchPackages) {
            try {
                return Class.forName(searchPackage + "." + nameCapitalized);
            } catch (ClassNotFoundException e) {
                System.err.printf("Класс %s не найден", nameCapitalized);
            }
        }
        return null; // TODO another
    }
}
