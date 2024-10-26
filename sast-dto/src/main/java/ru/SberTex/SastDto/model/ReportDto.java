package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;

import java.io.File;

/**
 * Data Transfer Object (DTO), представляющий отчет.
 * Используется для передачи данных об отчете и связанном проекте.
 *
 * @param file      Файл отчета. Не должен быть null.
 * @param projectId Идентификатор проекта, к которому относится отчет. Не должен быть null.
 */
public record ReportDto(@NotNull File file,
                        @NotNull Long projectId) {
}
