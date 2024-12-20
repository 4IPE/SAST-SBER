package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO), представляющий сущность проекта.
 * Используется для передачи данных проекта между различными слоями приложения.
 * <p>
 * Каждый экземпляр {@code ProjectDto} содержит следующие поля:
 * <ul>
 *     <li><strong>name</strong> - Название проекта. Не должно быть пустым, null или состоять из пробелов.</li>
 *     <li><strong>url</strong> - URL проекта. Не должен быть пустым, null или состоять из пробелов.</li>
 *     <li><strong>userId</strong> - Идентификатор пользователя, связанного с проектом. Не должен быть null.</li>
 * </ul>
 *
 * @param name   Название проекта. Должно быть заполнено.
 * @param url    URL проекта. Должно быть заполнено.
 * @param userId Идентификатор пользователя. Должен быть заполнен.
 */
public record ProjectDto(Long projectId,
                         @NotBlank @NotNull @NotEmpty String name,
                         @NotBlank @NotNull @NotEmpty String url,
                         @NotNull Long userId,
                         ReportDto reportDto) {
}
