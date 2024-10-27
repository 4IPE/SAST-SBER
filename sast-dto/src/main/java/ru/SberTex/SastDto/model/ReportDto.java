package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;

import java.io.File;


public record ReportDto(@NotNull File file,
                        @NotNull Long projectId) {
}
