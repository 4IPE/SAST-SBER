package ru.SberTex.SastManager.service;


import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;

import java.util.List;

public interface ProjectService {

    List<ProjectOutDto> getAllUsersProject(Long id,Integer from,Integer size);

    ProjectOutDto saveUsersProject(ProjectDto object);
}
