package com.marlonramon.predicts.service.mapper;

import com.marlonramon.predicts.domain.Championship;
import com.marlonramon.predicts.domain.Round;
import com.marlonramon.predicts.service.dto.ChampionshipDTO;
import com.marlonramon.predicts.service.dto.RoundDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Round} and its DTO {@link RoundDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoundMapper extends EntityMapper<RoundDTO, Round> {
    @Mapping(target = "championship", source = "championship", qualifiedByName = "championshipId")
    RoundDTO toDto(Round s);

    @Named("championshipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChampionshipDTO toDtoChampionshipId(Championship championship);
}
