package com.marlonramon.predicts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marlonramon.predicts.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bet.class);
        Bet bet1 = new Bet();
        bet1.setId(1L);
        Bet bet2 = new Bet();
        bet2.setId(bet1.getId());
        assertThat(bet1).isEqualTo(bet2);
        bet2.setId(2L);
        assertThat(bet1).isNotEqualTo(bet2);
        bet1.setId(null);
        assertThat(bet1).isNotEqualTo(bet2);
    }
}
