package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO), представляющий выходные данные отчета.
 * Используется для передачи данных о файле отчета, дате и времени создания, а также связанном проекте.
 *
 * @param file      Файл отчета. Не должен быть null.
 * @param data      Дата и время создания отчета. Должно быть в прошлом или настоящем.
 * @param projectId Идентификатор проекта, к которому относится отчет. Не должен быть null.
 */
public record ReportOutDto(@NotNull File file,
                           @NotNull @PastOrPresent LocalDateTime data,
                           @NotNull Long projectId) {
}
