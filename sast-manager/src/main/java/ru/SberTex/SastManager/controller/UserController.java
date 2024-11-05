package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;
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

    /**
     * Сохранение нового пользователя.
     *
     * @param object Объект UserDto для сохранения.
     * @return Объект UserOutDto с данными сохраненного пользователя.
     */
    @PostMapping
    public ResponseEntity<UserOutDto> saveUser(@RequestBody @Valid UserDto object) {
        log.info("Сохранение объекта пользователя : {}", object.toString());
        return ResponseEntity.ok().body(userService.saveUser(object));
    }

    /**
     * Обновление данных пользователя.
     *
     * @param object Объект UserDto с обновленными данными пользователя.
     * @return Статус успешного обновления.
     */
    @PatchMapping
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserDto object) {
        userService.updateUser(object);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Изменение объекта !");
    }
}
