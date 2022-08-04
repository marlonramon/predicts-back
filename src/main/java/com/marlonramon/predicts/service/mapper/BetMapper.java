package com.marlonramon.predicts.service.mapper;

import com.marlonramon.predicts.domain.Bet;
import com.marlonramon.predicts.domain.Match;
import com.marlonramon.predicts.domain.User;
import com.marlonramon.predicts.service.dto.BetDTO;
import com.marlonramon.predicts.service.dto.MatchDTO;
import com.marlonramon.predicts.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bet} and its DTO {@link BetDTO}.
 */
@Mapper(componentModel = "spring")
public interface BetMapper extends EntityMapper<BetDTO, Bet> {
    @Mapping(target = "match", source = "match", qualifiedByName = "matchId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    BetDTO toDto(Bet s);

    @Named("matchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatchDTO toDtoMatchId(Match match);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
