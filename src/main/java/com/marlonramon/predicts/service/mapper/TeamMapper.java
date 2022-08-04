package com.marlonramon.predicts.service.mapper;

import com.marlonramon.predicts.domain.Team;
import com.marlonramon.predicts.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {}
