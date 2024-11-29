package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.mapper.ProjectMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectOutDto> getAllUsersProject(Long id, Integer from, Integer size) {
//        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Order.desc("createdAt")));
//        return projectMapper.toListProjectOutDto(projectRepository.findById(id, pageable).stream().toList());

        return projectMapper.toListProjectOutDto(userRepository.findProjectsByUserId(id).stream().toList());
    }

    @Override
    public ProjectOutDto saveUsersProject(ProjectDto object) {
        log.info("Сохранение проекта: {}", object.toString());
        Project project = projectMapper.toProject(object);
        project.setCreatedAt(LocalDateTime.now().withSecond(0).withNano(0));
        User user = userRepository.findById(object.userId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.getProjects().add(project);
        userRepository.save(user);
        projectRepository.save(project);
        return projectMapper.toProjectOutDto(project);
    }
}
