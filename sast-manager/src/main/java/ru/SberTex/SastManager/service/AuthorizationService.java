package ru.SberTex.SastManager.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import ru.SberTex.SastDto.model.JwtAuthenticationResponse;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;


public interface AuthorizationService {

    void signUp(UserSingUpDto request, HttpServletResponse response);

    void signIn(UserSingInDto request, HttpServletResponse response);
}
