package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.service.ProjectService;
import ru.SberTex.SastManager.service.ReportService;

import java.util.List;

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
    private final ReportService reportService;

    /**
     * Получение списка проектов пользователя.
     *
     * @param id   Идентификатор пользователя.
     * @param from Начальный индекс для пагинации.
     * @param size Количество записей для отображения.
     * @return Список проектов пользователя.
     */
    @GetMapping("/get")
    public ResponseEntity<List<ProjectOutDto>> getAllUsersProject(@RequestParam(name = "id") Long id,
                                                                  @RequestParam(name = "from", required = false) Integer from,
                                                                  @RequestParam(name = "size", required = false) Integer size) {
        log.info("Получение данных у пользователя с id: {}", id);
        return ResponseEntity.ok().body(projectService.getAllUsersProject(id, from, size));
    }

    /**
     * Сохранение нового проекта.
     *
     * @param object Объект ProjectDto для сохранения.
     * @return Сохраненный объект ProjectOutDto с данными проекта.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveUsersProject(@RequestBody @Valid ProjectDto object) {
        log.info("Отправлен запрос на сохранения проекта: {}", object.toString());
        projectService.saveUsersProject(object);
        return ResponseEntity.status(HttpStatus.CREATED).body("Create Success");
    }

}
