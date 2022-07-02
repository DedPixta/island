package ru.javarush.island.kossatyy.repository.maps;

import ru.javarush.island.kossatyy.entity.creatures.Creature;

import java.util.HashMap;
import java.util.Set;

/*
Map<K,V> residents,where:
K - creature String type;
V - set of this creatures.
*/
public class Residents extends HashMap<String, Set<Creature>> {

}
