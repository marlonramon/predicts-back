package com.marlonramon.predicts.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marlonramon.predicts.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChampionshipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChampionshipDTO.class);
        ChampionshipDTO championshipDTO1 = new ChampionshipDTO();
        championshipDTO1.setId(1L);
        ChampionshipDTO championshipDTO2 = new ChampionshipDTO();
        assertThat(championshipDTO1).isNotEqualTo(championshipDTO2);
        championshipDTO2.setId(championshipDTO1.getId());
        assertThat(championshipDTO1).isEqualTo(championshipDTO2);
        championshipDTO2.setId(2L);
        assertThat(championshipDTO1).isNotEqualTo(championshipDTO2);
        championshipDTO1.setId(null);
        assertThat(championshipDTO1).isNotEqualTo(championshipDTO2);
    }
}
