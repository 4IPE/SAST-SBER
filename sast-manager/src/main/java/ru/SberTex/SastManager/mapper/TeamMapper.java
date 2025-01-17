package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.team.TeamOutDto;
import ru.SberTex.SastManager.model.Team;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, ProjectMapper.class})
public interface TeamMapper {

    TeamOutDto toTeamOutDto(Team team);
}
