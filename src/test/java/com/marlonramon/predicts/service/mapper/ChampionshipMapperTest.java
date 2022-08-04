package com.marlonramon.predicts.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChampionshipMapperTest {

    private ChampionshipMapper championshipMapper;

    @BeforeEach
    public void setUp() {
        championshipMapper = new ChampionshipMapperImpl();
    }
}
