package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object (DTO), представляющий данные пользователя.
 * Используется для передачи имени пользователя и пароля.
 *
 * @param username Имя пользователя. Не должно быть пустым, null или состоять из пробелов.
 * @param password Пароль пользователя. Не должен быть пустым, null или состоять из пробелов.
 */
public record UserSingInDto(@NotNull
                            @NotEmpty
                            @NotBlank
                            String username,

                            @NotNull
                            @NotEmpty
                            @NotBlank
                            String password) {
}
