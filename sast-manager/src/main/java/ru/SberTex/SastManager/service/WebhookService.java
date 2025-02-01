package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.ProjectDto;

import java.util.Map;

public interface WebhookService {
    ProjectDto PayloadToProjectDTO(Map<String, Object> payload);
}
