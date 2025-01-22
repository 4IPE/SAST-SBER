package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.TeamLightDto;
import ru.SberTex.SastDto.model.team.TeamOutDto;

import java.util.List;

public interface TeamService {

    List<TeamLightDto> getAllTeams(Long userId, Integer from, Integer size);

    TeamOutDto getTeamById(Long teamId);

    void addUserInTeam(Long teamId, String username);

    void kickUserFromTeam(Long teamId, String username);

    void deleteTeam(Long teamId, String username);

}

