package ru.SberTex.SastManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.SberTex.SastManager.service.UserService;

/**
 * Контроллер для управления пользователями.
 * Предоставляет API для создания и обновления данных пользователя.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

}
