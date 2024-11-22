package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.JwtAuthenticationResponse;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;
import ru.SberTex.SastManager.service.AuthorizationService;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid UserSingInDto request) {
        log.info("Отправлен запрос на вход пользователя: {}", request.username());
        return ResponseEntity.ok().body(authorizationService.singIn(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid UserSingUpDto request) {
        log.info("Отправлен запрос на сохранение пользователя: {}", request.username());
        return ResponseEntity.ok().body(authorizationService.singUp(request));
    }

}
