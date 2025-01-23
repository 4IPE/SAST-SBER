package ru.SberTex.SastAgent.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.SberTex.SastAgent.SASTAnalyzer;
import ru.SberTex.SastAgent.mapper.ProjectMapper;
import ru.SberTex.SastAgent.mapper.ReportMapper;
import ru.SberTex.SastAgent.service.AnalyzerService;
import ru.SberTex.SastDto.enumeration.Status;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastDto.model.ReportOutDto;

import java.util.Arrays;
import java.util.Set;

/**
 * Сервис для обработки сообщений из Kafka.
 * <p>
 * Этот класс слушает сообщения из темы "topic-agent" и выполняет анализ проекта,
 * используя SASTAnalyzer. Результаты анализа отправляются обратно в Kafka.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final ReportMapper reportMapper;
    private final ProjectMapper projectMapper;
    private final KafkaProducer kafkaProducer;
    private final AnalyzerService analyzerService;

    /**
     * Метод, который обрабатывает входящие сообщения из Kafka.
     *
     * @param message сообщение в формате JSON, представляющее проект.
     */
    @KafkaListener(topics = "topic-agent", groupId = "my-group")
    public void listen(String message) {
        Long reportId = 0L;
        try {
            // Преобразование сообщения в объект ProjectDto
            ProjectDto projectDto = objectMapper.readValue(message, ProjectDto.class);

            reportId = projectDto.getReportDto().getId();

            // Посылаем статус RUN
            analyzerService.patchReportStatus(reportId, Status.RUN);

            // Создание экземпляра SASTAnalyzer и выполнение анализа
            SASTAnalyzer analyzer = new SASTAnalyzer(projectDto.getId(), projectDto.getUrl());
            analyzer.cloneRepository();
            analyzer.buildProject();
            analyzer.analyze2();

            // Чтение отчета из файла
            StringBuilder content = analyzerService.getReportContent(analyzer);

            // Очистка временной папки
            analyzer.clearTempDirectory();

            log.info("REPORT AGENT {}", projectDto.getReportDto().getStatus());
            log.info("IDD AGENT {}", projectDto.getReportDto().getId());

            // Создание объекта отчета
            ReportOutDto reportOutDto = reportMapper.toReportOutDto(projectDto.getReportDto().getId(), content.toString(), projectDto.getId());

            // Создание объекта проекта с отчётом
            ProjectOutDto projectOutDto = projectMapper.toProjectOutDto(projectDto, Set.of(reportOutDto));
            log.info("REPORT AGENT OUT {}", projectOutDto.reports());
            log.info("IDD AGENT OUT {}", reportOutDto.id());
            //отправка проекта в manager
            kafkaProducer.sendMessageInManager(projectOutDto);
        } catch (Exception e) {
            // Посылаем статус ERROR
            analyzerService.patchReportStatus(reportId, Status.ERROR);

            System.out.println("------------------------------------------");
            log.error(Arrays.toString(e.getStackTrace()));
            System.out.println("------------------------------------------");
            throw new RuntimeException("RuntimeException: " + e.getMessage());
        }
    }
}
