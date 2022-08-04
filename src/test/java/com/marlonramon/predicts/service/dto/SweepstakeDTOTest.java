package com.marlonramon.predicts.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marlonramon.predicts.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SweepstakeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SweepstakeDTO.class);
        SweepstakeDTO sweepstakeDTO1 = new SweepstakeDTO();
        sweepstakeDTO1.setId(1L);
        SweepstakeDTO sweepstakeDTO2 = new SweepstakeDTO();
        assertThat(sweepstakeDTO1).isNotEqualTo(sweepstakeDTO2);
        sweepstakeDTO2.setId(sweepstakeDTO1.getId());
        assertThat(sweepstakeDTO1).isEqualTo(sweepstakeDTO2);
        sweepstakeDTO2.setId(2L);
        assertThat(sweepstakeDTO1).isNotEqualTo(sweepstakeDTO2);
        sweepstakeDTO1.setId(null);
        assertThat(sweepstakeDTO1).isNotEqualTo(sweepstakeDTO2);
    }
}
