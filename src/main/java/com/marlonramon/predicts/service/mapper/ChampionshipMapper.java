package com.marlonramon.predicts.service.mapper;

import com.marlonramon.predicts.domain.Championship;
import com.marlonramon.predicts.domain.Sweepstake;
import com.marlonramon.predicts.service.dto.ChampionshipDTO;
import com.marlonramon.predicts.service.dto.SweepstakeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Championship} and its DTO {@link ChampionshipDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChampionshipMapper extends EntityMapper<ChampionshipDTO, Championship> {
    @Mapping(target = "sweepstake", source = "sweepstake", qualifiedByName = "sweepstakeId")
    ChampionshipDTO toDto(Championship s);

    @Named("sweepstakeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SweepstakeDTO toDtoSweepstakeId(Sweepstake sweepstake);
}
