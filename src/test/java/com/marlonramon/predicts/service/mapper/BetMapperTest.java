package com.marlonramon.predicts.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetMapperTest {

    private BetMapper betMapper;

    @BeforeEach
    public void setUp() {
        betMapper = new BetMapperImpl();
    }
}
