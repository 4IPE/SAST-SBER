package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.JwtAuthenticationResponse;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;


public interface AuthorizationService {

    JwtAuthenticationResponse singIn(UserSingInDto userSingInDto);

    JwtAuthenticationResponse singUp(UserSingUpDto userSingUpDto);
}
