package com.marlonramon.predicts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marlonramon.predicts.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SweepstakeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sweepstake.class);
        Sweepstake sweepstake1 = new Sweepstake();
        sweepstake1.setId(1L);
        Sweepstake sweepstake2 = new Sweepstake();
        sweepstake2.setId(sweepstake1.getId());
        assertThat(sweepstake1).isEqualTo(sweepstake2);
        sweepstake2.setId(2L);
        assertThat(sweepstake1).isNotEqualTo(sweepstake2);
        sweepstake1.setId(null);
        assertThat(sweepstake1).isNotEqualTo(sweepstake2);
    }
}
