package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.io.File;
import java.time.LocalDateTime;

public record ReportOutDto(@NotNull File file,
                           @NotNull @PastOrPresent LocalDateTime data,
                           @NotNull Long projectId) {
}
