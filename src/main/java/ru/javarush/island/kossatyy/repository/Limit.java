package ru.javarush.island.kossatyy.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Limit {
    double maxWeight;
    int maxPopulation;
    int speed;

}
