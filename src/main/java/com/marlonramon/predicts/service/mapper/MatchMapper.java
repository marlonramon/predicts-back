package com.marlonramon.predicts.service.mapper;

import com.marlonramon.predicts.domain.Match;
import com.marlonramon.predicts.domain.Round;
import com.marlonramon.predicts.domain.Team;
import com.marlonramon.predicts.service.dto.MatchDTO;
import com.marlonramon.predicts.service.dto.RoundDTO;
import com.marlonramon.predicts.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Match} and its DTO {@link MatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchMapper extends EntityMapper<MatchDTO, Match> {
    @Mapping(target = "round", source = "round", qualifiedByName = "roundId")
    @Mapping(target = "homeTeam", source = "homeTeam", qualifiedByName = "teamId")
    @Mapping(target = "visitantTeam", source = "visitantTeam", qualifiedByName = "teamId")
    MatchDTO toDto(Match s);

    @Named("roundId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoundDTO toDtoRoundId(Round round);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);
}
