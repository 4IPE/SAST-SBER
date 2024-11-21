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
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.service.ProjectService;
import ru.SberTex.SastManager.service.ReportService;

import java.util.List;

/**
 * Контроллер для управления проектами и отчетами.
 * Предоставляет API для работы с проектами и сохранения отчетов.
 */
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
                                                                  @RequestParam(name = "from") Integer from,
                                                                  @RequestParam(name = "size") Integer size) {
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
    public ResponseEntity<ProjectOutDto> saveUsersProject(@RequestBody @Valid ProjectDto object) {
        log.info("Отправлен запрос на сохранения проекта: {}", object.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.saveUsersProject(object));
    }

    /**
     * Сохранение отчета для проекта.
     *
     * @param reportDto Объект ReportDto для сохранения отчета.
     * @return Сохраненный объект ReportOutDto с данными отчета.
     */
    @PostMapping("/save/report")
    public ResponseEntity<ReportOutDto> saveProjectReports(@RequestBody @Valid ReportDto reportDto) {
        log.info("Отправлен запрос на сохранения репорта для проекта: {}", reportDto.toString());
        return ResponseEntity.ok().body(reportService.saveProjectReports(reportDto));
    }
}

