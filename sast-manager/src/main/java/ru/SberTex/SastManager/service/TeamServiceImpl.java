package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.TeamLightDto;
import ru.SberTex.SastDto.model.team.TeamOutDto;
import ru.SberTex.SastManager.exception.FewRightsException;
import ru.SberTex.SastManager.exception.NotFoundException;
import ru.SberTex.SastManager.mapper.TeamMapper;
import ru.SberTex.SastManager.model.Team;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.TeamRepository;
import ru.SberTex.SastManager.security.jwt.JwtTokenProvider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<TeamLightDto> getAllTeams(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return teamRepository.findByTeammates_id(userId)
                .stream().map(teamMapper::toTeamLightDto).collect(Collectors.toList());
    }

    @Override
    public TeamOutDto getTeamById(Long teamId) {
        return teamMapper.toTeamOutDto(teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Команда не найдена")));
    }

    @Override
    public void addUserInTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Команда с id: " + teamId + " не была найден "));

        User user = userService.getUserByUsername(username);
        Set<User> teammates = team.getTeammates();
        if (teammates.contains(user)) {
            throw new RuntimeException("Пользователь " + username + " уже в команде");
        }
        teammates.add(user);
        teamRepository.save(team);
    }

    @Override
    public void kickUserFromTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Команда с id: " + teamId + " не была найден "));

        User user = userService.getUserByUsername(username);

        if (team.getProject().getOwner().getUsername().equalsIgnoreCase(username)) {
            throw new FewRightsException("Вы не можете исключить себя из команды");
        }

        Set<User> teammates = team.getTeammates();
        teammates.removeIf(findUser -> username.equalsIgnoreCase(findUser.getUsername()));
        team.setTeammates(teammates);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Команда с id: " + teamId + " не была найден "));

        User user = userService.getUserByUsername(username);

        if (!team.getProject().getOwner().getUsername().equalsIgnoreCase(username)) {
            throw new FewRightsException("Недостаточно прав чтобы удалить данную команду");
        }
        teamRepository.deleteById(teamId);
    }


    @Override
    public void createToken(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException("Команда не была найдена"));
        String token = jwtTokenProvider.createTokenForAPI(team.getId());
        team.setToken(token);
        teamRepository.save(team);
    }


}
