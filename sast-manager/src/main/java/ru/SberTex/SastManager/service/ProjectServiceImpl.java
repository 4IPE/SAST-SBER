package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.exception.RemoteRepoNotFoundException;
import ru.SberTex.SastManager.mapper.ProjectMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.Team;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.ProjectRepository;
import ru.SberTex.SastManager.repository.TeamRepository;
import ru.SberTex.SastManager.repository.UserRepository;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Сервисный класс для управления проектами.
 *
 * @see ru.SberTex.SastManager.service.ProjectService
 * @see ru.SberTex.SastDto.model.ProjectDto
 * @see ru.SberTex.SastDto.model.ProjectOutDto
 * @see ru.SberTex.SastManager.mapper.ProjectMapper
 * @see ru.SberTex.SastManager.repository.ProjectRepository
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<ProjectOutDto> getAllProjects(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Order.desc("createdAt")));
        return projectMapper.toListProjectOutDto(projectRepository.findByTeam_Teammates_Id(userId));
    }

    @Override
    public void saveProject(ProjectDto object) {
        if (projectRepository.existsByUrl(object.getUrl())) {
            throw new RuntimeException("Данный проект уже существует");
        }
        Project project = projectMapper.toProject(object);
        User user = userRepository.findById(object.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователь с ID " + object.getUserId() + " не найден"));
        project.setOwner(user);
        projectRepository.save(project);

        //Добавление команды
        Team team = new Team();
        team.setName(project.getName());
        team.setProject(project);
        team.setTeammates(Set.of(project.getOwner()));
        teamRepository.save(team);
    }

    @Override
    public Project getProjectWithId(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Проект не найден"));
        Hibernate.initialize(project.getReports());
        return project;
    }

    @Override
    public Project getProjectByUrl(String url) {
        Project proj = projectRepository.findByUrl(url);
        if (proj == null) {
            throw new RuntimeException("Данного проекта не существует");
        }
        return proj;
    }

    @Override
    public void checkRemoteRepo(String url) throws Exception {
        URL repoUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) repoUrl.openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new RemoteRepoNotFoundException();
        }
        connection.disconnect();
    }

}
