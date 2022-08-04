package com.marlonramon.predicts.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marlonramon.predicts.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BetDTO.class);
        BetDTO betDTO1 = new BetDTO();
        betDTO1.setId(1L);
        BetDTO betDTO2 = new BetDTO();
        assertThat(betDTO1).isNotEqualTo(betDTO2);
        betDTO2.setId(betDTO1.getId());
        assertThat(betDTO1).isEqualTo(betDTO2);
        betDTO2.setId(2L);
        assertThat(betDTO1).isNotEqualTo(betDTO2);
        betDTO1.setId(null);
        assertThat(betDTO1).isNotEqualTo(betDTO2);
    }
}
