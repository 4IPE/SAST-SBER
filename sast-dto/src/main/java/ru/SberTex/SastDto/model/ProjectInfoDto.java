package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * Data Transfer Object (DTO), представляющий выходные данные проекта.
 * Используется для передачи данных проекта вместе с отчетами.
 *
 * @param name    Название проекта. Не должно быть пустым, null или состоять из пробелов.
 * @param url     URL проекта. Не должно быть пустым, null или состоять из пробелов.
 * @param userId  Идентификатор пользователя, связанного с проектом. Не должен быть null.
 */
public record ProjectInfoDto(@NotNull Long id,
                             @NotBlank @NotNull @NotEmpty String name,
                             @NotBlank @NotNull @NotEmpty String url,
                             @NotNull Long userId) {
}
