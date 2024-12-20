package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import ru.SberTex.SastDto.enumeration.Status;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO), представляющий выходные данные отчета.
 * Используется для передачи данных о файле отчета, дате и времени создания, а также связанном проекте.
 *
 * @param content   Содержание отчета. Не должно быть null.
 * @param createdAt Дата и время создания отчета. Должно быть в прошлом или настоящем.
 * @param projectId Идентификатор проекта, к которому относится отчет. Не должен быть null.
 */
public record ReportOutDto(Long id,
                           @NotNull String content,
                           @NotNull @PastOrPresent LocalDateTime createdAt,
                           @NotNull Long projectId,
                           Status status) {
}
