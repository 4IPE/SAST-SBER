package ru.SberTex.SastAgent.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.SberTex.SastAgent.SASTAnalyzer;
import ru.SberTex.SastAgent.mapper.ProjectMapper;
import ru.SberTex.SastAgent.mapper.ReportMapper;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastDto.model.ReportOutDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    /**
     * Метод, который обрабатывает входящие сообщения из Kafka.
     *
     * @param message сообщение в формате JSON, представляющее проект.
     */
    @KafkaListener(topics = "topic-agent", groupId = "my-group")
    public void listen(String message) {
        try {
            // Преобразование сообщения в объект ProjectDto
            ProjectDto projectDto = objectMapper.readValue(message, ProjectDto.class);

            // Создание экземпляра SASTAnalyzer и выполнение анализа
            SASTAnalyzer analyzer = new SASTAnalyzer(projectDto.getId(), projectDto.getUrl());
            analyzer.cloneRepository();
            analyzer.buildProject();
            analyzer.analyze();

            // Чтение отчета из файла
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(analyzer.getReportRelativePath()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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
            System.out.println("------------------------------------------");
            log.error(Arrays.toString(e.getStackTrace()));
            System.out.println("------------------------------------------");
            throw new RuntimeException("RuntimeException: " + e.getMessage());
        }
    }
}
