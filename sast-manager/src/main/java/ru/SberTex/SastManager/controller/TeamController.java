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

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<?> getAllTeams(@PathVariable(name = "userId") Long userId,
                                         @RequestParam(name = "from", required = false) Integer from,
                                         @RequestParam(name = "size", required = false) Integer size) {
        try {
            log.info("Получение команд у пользователя с id: {}", userId);
            return ResponseEntity.ok().body(teamService.getAllTeams(userId, from, size));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/get/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable Long teamId) {
        try {
            log.info("Получение команды с id: {}", teamId);
            return ResponseEntity.ok().body(teamService.getTeamById(teamId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserInTeam(@RequestParam Long teamId, @RequestParam String username) {
        try {
            log.info("Отправлен запрос на добавление в команду: {}", username);
            teamService.addUserInTeam(teamId, username);
            return ResponseEntity.ok().body("Проект сохранен");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/kick")
    public ResponseEntity<?> kickUserFromTeam(@RequestParam Long teamId, @RequestParam String username) {
        try {
            log.info("Отправлен запрос на исключение из команды: {}", username);
            teamService.kickUserFromTeam(teamId, username);
            return ResponseEntity.ok().body("Успешно");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestParam Long teamId, @RequestParam String username) {
        try {
            log.info("Отправлен запрос на удаление команды с id : {}", teamId);
            teamService.deleteTeam(teamId, username);
            return ResponseEntity.ok().body("Успешно");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/create/token")
    public ResponseEntity<?> createTokenTeam(@RequestParam Long teamId) {
        try {
            log.info("Отправлен запрос на создание токена  с id : {}", teamId);
            teamService.createToken(teamId);
            return ResponseEntity.ok().body("Успешно");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
