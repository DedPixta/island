package com.javarush.island.kossatyy.entity;

import com.javarush.island.kossatyy.entity.map.Island;
import com.javarush.island.kossatyy.entity.repository.Factory;
import com.javarush.island.kossatyy.view.View;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Game {

    private final Island island;
    private final Factory entityFactory;
    private final View view;

}
