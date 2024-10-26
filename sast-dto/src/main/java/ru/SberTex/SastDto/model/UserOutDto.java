package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * Data Transfer Object (DTO), представляющий выходные данные пользователя.
 * Используется для передачи имени пользователя, ролей и проектов.
 *
 * @param username Имя пользователя. Не должно быть пустым, null или состоять из пробелов.
 * @param roles    Набор ролей пользователя. Не должен быть пустым или null.
 * @param projects Набор проектов, связанных с пользователем. Не должен быть пустым или null.
 */
public record UserOutDto(@NotNull @NotEmpty @NotBlank String username,
                         @NotNull @NotEmpty Set<RoleDto> roles,
                         @NotNull @NotEmpty Set<ProjectOutDto> projects) {
}
