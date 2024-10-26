package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.mapper.ProjectMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectOutDto> getAllUsersProject(Long id, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Order.desc("timeCreate")));

        return projectMapper.toListProjectOutDto(projectRepository.findById(id, pageable).stream().toList());
    }

    @Override
    public ProjectOutDto saveUsersProject(ProjectDto object) {
        Project project = projectMapper.toProject(object);
        project.setTimeCreate(LocalDateTime.now().withSecond(0).withNano(0));
        return projectMapper.toProjectOutDto(projectRepository.save(project));
    }
}
