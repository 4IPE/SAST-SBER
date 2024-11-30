package ru.SberTex.SastManager.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import ru.SberTex.SastDto.model.JwtAuthenticationResponse;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;


public interface AuthorizationService {

    @Transactional
    void singUp(UserSingUpDto request, HttpServletResponse response);

    @Transactional
    void singIn(UserSingInDto request, HttpServletResponse response);
}
