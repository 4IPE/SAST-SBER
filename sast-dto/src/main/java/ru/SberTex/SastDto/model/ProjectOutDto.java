package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record ProjectOutDto(@NotBlank @NotNull @NotEmpty String name,
                            @NotBlank @NotNull @NotEmpty String url,
                            @NotNull Long userId,
                            @NotNull @NotEmpty Set<ReportOutDto> reports) {
}
