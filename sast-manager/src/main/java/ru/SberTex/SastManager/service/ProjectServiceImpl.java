package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.mapper.ProjectMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.ProjectRepository;
import ru.SberTex.SastManager.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<ProjectOutDto> getAllProjects(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Order.desc("createdAt")));
        return projectMapper.toListProjectOutDto(projectRepository.findById(userId).stream().toList());
    }

    @Override
    public void saveProject(ProjectDto object) {
        if (projectRepository.findByUrl(object.url()) != null) {
            throw new RuntimeException("Данный проект уже существует");
        }
        Project project = projectMapper.toProject(object);
        User user = userRepository.findById(object.userId())
                .orElseThrow(() -> new RuntimeException("Пользователь с ID " + object.userId() + " не найден"));
        project.setCreatedAt(LocalDateTime.now().withSecond(0).withNano(0));
        project.setOwner(user);
        project.addUser(user);
        projectRepository.save(project);
    }

    @Override
    public Project getProjectWithId(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Проект не найден"));
    }

    @Override
    public Project getProjectByUrl(String url) {
        Project proj = projectRepository.findByUrl(url);
        if (proj == null) {
            throw new RuntimeException("Данного проекта не существует");
        }
        return proj;
    }
}
