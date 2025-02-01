package ru.SberTex.SastManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastManager.mapper.ProjectMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.service.ProjectService;
import ru.SberTex.SastManager.service.ReportService;
import ru.SberTex.SastManager.service.WebhookService;

import java.util.Map;


@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {
    private final ReportService reportService;
    private final WebhookService webhookService;

    /*
    пример вебхука
    {
      "ref": "refs/heads/feature-branch",
      "repository": {
        "html_url": "https://github.com/username/example-repo"
      }
    }
    */
    @PostMapping("/push")
    public ResponseEntity<?> handlePushEvent(@RequestBody Map<String, Object> payload) {
        try {
            log.info("Получена webhook-информация: {}", payload.get("ref"));
            ProjectDto projectDto = webhookService.PayloadToProjectDTO(payload);
            log.info("Отправлен запрос на сохранения репорта: {}", projectDto.toString());
            reportService.createReport(projectDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("webhook обработан успешно");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Возникла ошибка при работе с webhook: " + e.getMessage());
        }
    }
}
