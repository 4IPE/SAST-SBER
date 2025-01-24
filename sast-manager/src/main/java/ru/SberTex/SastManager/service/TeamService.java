package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import ru.SberTex.SastDto.model.TeamLightDto;
import ru.SberTex.SastDto.model.team.TeamOutDto;

import java.util.List;

public interface TeamService {

    List<TeamLightDto> getAllTeams(Long userId, Integer from, Integer size);

    TeamOutDto getTeamById(Long teamId);

    @Transactional
    void addUserInTeam(Long teamId, String username);

    @Transactional
    void kickUserFromTeam(Long teamId, String username);

    @Transactional
    void deleteTeam(Long teamId, String username);

    @Transactional
    void createToken(Long teamId);
}

