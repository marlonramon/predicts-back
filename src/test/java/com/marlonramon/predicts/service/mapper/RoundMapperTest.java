package com.marlonramon.predicts.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoundMapperTest {

    private RoundMapper roundMapper;

    @BeforeEach
    public void setUp() {
        roundMapper = new RoundMapperImpl();
    }
}
