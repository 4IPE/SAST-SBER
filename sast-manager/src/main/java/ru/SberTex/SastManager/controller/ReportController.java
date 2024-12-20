package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastManager.service.ReportService;

import java.util.Map;

/**
 * Контроллер для управления проектами и отчетами.
 * Предоставляет API для работы с проектами и сохранения отчетов.
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/get/{projectId}")
    public ResponseEntity<?> getAllReports(@PathVariable(name = "projectId") Long projectId) {
        try {
            log.info("Получение данных у проекта с id: {}", projectId);
            return ResponseEntity.ok().body(reportService.getAllReportsWithProjectId(projectId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReport(@RequestBody @Valid ProjectDto object) {
        try {
            log.info("Отправлен запрос на сохранения репорта: {}", object.toString());
            reportService.createReport(object);
            return ResponseEntity.ok().body("Отчет сохранен");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReport(@RequestBody @Valid ReportDto object) {
        try {
            log.info("Отправлен запрос на сохранения репорта: {}", object.toString());
            reportService.saveReportProject(object);
            return ResponseEntity.ok().body("Отчет сохранен");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


}
