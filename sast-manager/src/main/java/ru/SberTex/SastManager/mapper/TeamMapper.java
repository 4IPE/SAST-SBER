package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.TeamLightDto;
import ru.SberTex.SastDto.model.team.TeamOutDto;
import ru.SberTex.SastManager.model.Team;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProjectMapper.class})
public interface TeamMapper {

    TeamLightDto toTeamLightDto(Team team);

    TeamOutDto toTeamOutDto(Team team);
}
