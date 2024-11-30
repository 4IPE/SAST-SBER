package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO), представляющий выходные данные отчета.
 * Используется для передачи данных о файле отчета, дате и времени создания, а также связанном проекте.
 *
 * @param content   Содержание отчета. Не должно быть null.
 * @param createAt      Дата и время создания отчета. Должно быть в прошлом или настоящем.
 * @param projectId Идентификатор проекта, к которому относится отчет. Не должен быть null.
 */
public record ReportOutDto(@NotNull String content,
                           @NotNull @PastOrPresent LocalDateTime createAt,
                           @NotNull Long projectId) {
}
