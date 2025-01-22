package ru.SberTex.SastDto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamLightDto {
    private Long id;
    private String name;
    private ProjectInfoDto project;
}
