package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.SberTex.SastDto.model.JwtAuthenticationResponse;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;
import ru.SberTex.SastManager.service.AuthorizationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid UserSingUpDto request) {
        return ResponseEntity.ok().body(authorizationService.singUp(request));
    }


    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid UserSingInDto request) {
        return ResponseEntity.ok().body(authorizationService.singIn(request));
    }
}
