package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.team.TeamOutDto;
import ru.SberTex.SastManager.exception.FewRightsException;
import ru.SberTex.SastManager.exception.NotFoundException;
import ru.SberTex.SastManager.mapper.TeamMapper;
import ru.SberTex.SastManager.model.Team;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.TeamRepository;
import ru.SberTex.SastManager.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;

    @Override
    public List<TeamOutDto> getAllTeamsUser(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return teamRepository.findByTeammate_id(userId).stream().map(teamMapper::toTeamOutDto).collect(Collectors.toList());
    }

    @Override
    public void addUserInTeam(String login, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException("Команда с id: " + teamId + " не был найден "));
        User user = userRepository.findByUsername(login);

        if (user == null) {
            throw new NotFoundException("Пользователь с username: " + login + " не был найден ");
        }
        Set<User> teammates = team.getTeammate();
        teammates.add(user);
        team.setTeammate(teammates);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long teamId, String login) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException("Команда с id: " + teamId + " не был найден "));
        User user = userRepository.findByUsername(login);
        if (user == null) {
            throw new NotFoundException("Пользователь с username: " + login + " не был найден ");
        }
        if(!team.getProject().getOwner().getUsername().equalsIgnoreCase(login)){
            throw new FewRightsException("Недостаточно прав чтобы удалить данную команду");
        }
        teamRepository.deleteById(teamId);
    }

    @Override
    public void kickUserFromTeam(Long teamId, String login) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException("Команда с id: " + teamId + " не был найден "));
        User user = userRepository.findByUsername(login);

        if (user == null) {
            throw new NotFoundException("Пользователь с username: " + login + " не был найден ");
        }

        Set<User> teammates = team.getTeammate();
        teammates.removeIf(findUser -> login.equalsIgnoreCase(findUser.getUsername()));
        team.setTeammate(teammates);
        teamRepository.save(team);
    }

}
