package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;

import java.io.File;

/**
 * Data Transfer Object (DTO), представляющий отчет.
 * Используется для передачи данных об отчете и связанном проекте.
 *
 * @param content   Содержание отчета. Не должно быть null.
 * @param projectId Идентификатор проекта, к которому относится отчет. Не должен быть null.
 */
public record ReportDto(@NotNull String content,
                        @NotNull Long projectId) {
}
