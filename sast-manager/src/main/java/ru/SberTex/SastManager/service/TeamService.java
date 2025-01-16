package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.team.TeamOutDto;

import java.util.List;

public interface TeamService {

    List<TeamOutDto> getAllTeamsUser(Long userId, Integer from, Integer size);

    void addUserInTeam(String login,Long id);

    void deleteTeam(Long teamId, String login);

    void kickUserFromTeam(Long teamId, String login);
}

