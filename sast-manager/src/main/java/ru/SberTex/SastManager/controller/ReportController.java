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
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.service.ProjectService;
import ru.SberTex.SastManager.service.ReportService;

import java.util.List;

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
    public ResponseEntity<List<ReportOutDto>> getAllReportsWithProjectId(@PathVariable(name = "projectId") Long projectId) {
        log.info("Получение данных у проекта с id: {}", projectId);
        return ResponseEntity.ok().body(reportService.getAllReportsWithProjectId(projectId));
    }

}
