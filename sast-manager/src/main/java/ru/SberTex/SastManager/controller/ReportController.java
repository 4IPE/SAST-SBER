package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastManager.service.ReportService;

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


    @PostMapping("/save")
    public ResponseEntity<String> reportSave(@RequestBody @Valid ProjectDto object) {
        log.info("Отправлен запрос на сохранения репорта: {}", object.toString());
        reportService.createReport(object);
        return ResponseEntity.status(HttpStatus.CREATED).body("Create Success");
    }

}
