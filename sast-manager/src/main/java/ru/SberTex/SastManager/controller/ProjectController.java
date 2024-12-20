package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastManager.service.ProjectService;

import java.util.Map;

/**
 * Контроллер для управления проектами и отчетами.
 * Предоставляет API для работы с проектами и сохранения отчетов.
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProjectController {
    private final ProjectService projectService;

    /**
     * Получение списка проектов пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param from   Начальный индекс для пагинации.
     * @param size   Количество записей для отображения.
     * @return Список проектов пользователя.
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getAllProjects(@PathVariable(name = "userId") Long userId,
                                                @RequestParam(name = "from", required = false) Integer from,
                                                @RequestParam(name = "size", required = false) Integer size) {
        try {
            log.info("Получение данных у пользователя с id: {}", userId);
            return ResponseEntity.ok().body(projectService.getAllProjects(userId, from, size));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Сохранение нового проекта.
     *
     * @param object Объект ProjectDto для сохранения.
     * @return Сохраненный объект ProjectOutDto с данными проекта.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveProject(@RequestBody @Valid ProjectDto object) {
        try {
            log.info("Отправлен запрос на сохранение проекта: {}", object.toString());
            projectService.saveProject(object);
            return ResponseEntity.ok().body("Проект сохранен");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


}
