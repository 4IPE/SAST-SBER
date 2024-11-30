package ru.SberTex.SastDto.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {
    private  String username;
    private Set<ProjectOutDto> projects;
    private String email;
}