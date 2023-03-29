package com.island.entity;

import com.island.entity.map.Island;
import com.island.repository.factory.Factory;
import com.island.view.View;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Game {
    private final Island island;
    private final Factory entityFactory;
    private final View view;
}
