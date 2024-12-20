package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    Long id;
    @NotBlank @NotNull @NotEmpty String name;
    @NotBlank @NotNull @NotEmpty String url;
    @NotNull Long userId;
    ReportDto reportDto;
}
