package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastManager.mapper.ProjectMapper;
import ru.SberTex.SastManager.model.Project;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto PayloadToProjectDTO(Map<String, Object> payload) {
        Map<String, Object> repo = (Map<String, Object>) payload.get("repository");

        String url = (String) repo.get("html_url") + ".git";
        String ref = (String) payload.get("ref");
        String branchName = ref.replace("refs/heads/", "");

        Project project = projectService.getProjectByUrl(url);
        ProjectDto projectDto = projectMapper.toProjectDto(project);
        return projectDto;
    }
}
