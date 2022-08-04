package com.marlonramon.predicts.service.mapper;

import com.marlonramon.predicts.domain.Sweepstake;
import com.marlonramon.predicts.domain.User;
import com.marlonramon.predicts.service.dto.SweepstakeDTO;
import com.marlonramon.predicts.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sweepstake} and its DTO {@link SweepstakeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SweepstakeMapper extends EntityMapper<SweepstakeDTO, Sweepstake> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "users", source = "users", qualifiedByName = "userIdSet")
    SweepstakeDTO toDto(Sweepstake s);

    @Mapping(target = "removeUser", ignore = true)
    Sweepstake toEntity(SweepstakeDTO sweepstakeDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<UserDTO> toDtoUserIdSet(Set<User> user) {
        return user.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }
}
