package ru.SberTex.SastManager.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;
import ru.SberTex.SastManager.service.AuthorizationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserSingInDto request, HttpServletResponse response) {
        log.info("Отправлен запрос на вход пользователя: {}", request.username());
        System.out.println("Отправлен запрос на вход пользователя");
        authorizationService.singIn(request, response);
        return ResponseEntity.ok().body("Login success");
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserSingUpDto request, HttpServletResponse response) {
        log.info("Отправлен запрос на сохранение пользователя: {}", request.username());
        System.out.println("Отправлен запрос на сохранение пользователя");
        authorizationService.singUp(request, response);
        return ResponseEntity.ok().body("Registry success");
    }

}
