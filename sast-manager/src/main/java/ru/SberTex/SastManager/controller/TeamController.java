package ru.SberTex.SastManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastManager.service.TeamService;

import java.util.Map;


@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getAllTeam(@PathVariable(name = "userId") Long userId,
                                        @RequestParam(name = "from", required = false) Integer from,
                                        @RequestParam(name = "size", required = false) Integer size) {
        try {
            log.info("Получение данных у пользователя с id: {}", userId);
            return ResponseEntity.ok().body(teamService.getAllTeamsUser(userId, from, size));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addUserInTeam(@RequestParam String login, @RequestParam Long id) {
        try {
            log.info("Отправлен запрос на добавление в команду: {}", login);
            teamService.addUserInTeam(login, id);
            return ResponseEntity.ok().body("Проект сохранен");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/kick")
    public ResponseEntity<?> kickUserFromTeam(@RequestParam String login, @RequestParam Long id) {
        try {
            log.info("Отправлен запрос на исключение из команды: {}", login);
            teamService.kickUserFromTeam(id, login);
            return ResponseEntity.ok().body("Успешно");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestParam Long id, @RequestParam String login) {
        try {
            log.info("Отправлен запрос на удаление  команды с id : {}", id);
            teamService.deleteTeam(id, login);
            return ResponseEntity.ok().body("Успешно");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
