package com.marlonramon.predicts.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SweepstakeMapperTest {

    private SweepstakeMapper sweepstakeMapper;

    @BeforeEach
    public void setUp() {
        sweepstakeMapper = new SweepstakeMapperImpl();
    }
}
