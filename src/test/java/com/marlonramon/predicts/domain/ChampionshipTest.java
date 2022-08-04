package com.marlonramon.predicts.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marlonramon.predicts.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChampionshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Championship.class);
        Championship championship1 = new Championship();
        championship1.setId(1L);
        Championship championship2 = new Championship();
        championship2.setId(championship1.getId());
        assertThat(championship1).isEqualTo(championship2);
        championship2.setId(2L);
        assertThat(championship1).isNotEqualTo(championship2);
        championship1.setId(null);
        assertThat(championship1).isNotEqualTo(championship2);
    }
}
