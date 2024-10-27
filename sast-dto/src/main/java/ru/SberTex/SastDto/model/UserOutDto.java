package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record UserOutDto(@NotNull @NotEmpty @NotBlank String username,
                         @NotNull @NotEmpty Set<RoleDto> roles ,
                         @NotNull @NotEmpty Set<ProjectOutDto> projects) {
}
