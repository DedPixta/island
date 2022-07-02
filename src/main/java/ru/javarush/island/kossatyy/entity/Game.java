package ru.javarush.island.kossatyy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.javarush.island.kossatyy.entity.map.Island;
import ru.javarush.island.kossatyy.repository.factory.Factory;
import ru.javarush.island.kossatyy.view.View;

@Getter
@RequiredArgsConstructor
public class Game {

    private final Island island;
    private final Factory entityFactory;
    private final View view;

}
