package ru.javarush.island.kossatyy.repository.maps;

import java.util.HashMap;

/*
Map<K,V> foodMap,where:
K - creature String type;
V - creature ration with chanceToEat > 0 (Map<String creature, Integer chanceToEat>).
*/
public class FoodMap extends HashMap<String, Ration> {

}
