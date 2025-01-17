package ru.SberTex.SastDto.model.team;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.SberTex.SastDto.model.ProjectInfoDto;
import ru.SberTex.SastDto.model.UserOutDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TeamOutDto {
    private Long id;
    private String name;
    private ProjectInfoDto projectInfoDto;
    private Set<UserOutDto> teammate;
}
