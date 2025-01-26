package ru.SberTex.SastManager.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.UserUpdateDto;
import ru.SberTex.SastManager.mapper.UserMapper;
import ru.SberTex.SastManager.service.UserService;

import java.util.Map;

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
    private final UserMapper userMapper;

    @GetMapping("/get")
    public ResponseEntity<?> getUserFromToken(HttpServletRequest request) {
        return ResponseEntity.ok(userMapper.toUserOutDto(userService.getUserWithCookie(request)));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getUserStatus(HttpServletRequest request) {
        return userService.validCookies(request);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserUpdateDto userUpdateDto, HttpServletRequest request) {
        try {
            log.info("Запрос на обновление профиля пользователя {}", userUpdateDto.getUsername());
            userService.updateUserProfile(userUpdateDto, request);
            return ResponseEntity.ok("Профиль успешно обновлен");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/edit")
    public ResponseEntity<?> editPassword(@RequestParam String token, @RequestParam String password) {
        try {
            log.info("Запрос на редактирование пароля");
            userService.editPassword(token, password);
            return ResponseEntity.ok().body("Пароль успешно обновлен");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }

    }

    @PostMapping("/request")
    public ResponseEntity<?> requestForEditPassword(@RequestParam String email) {
        try {
            log.info("Запрос на восстановление пароля пользователя с почтой {}", email);
            userService.requestForEditPassword(email);
            return ResponseEntity.ok().body("На вашу почту отправлено письмо для восстановления доступа");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


}
