package ru.SberTex.SastDto.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {
    private Long id;
    private String username;
    private String email;
}
