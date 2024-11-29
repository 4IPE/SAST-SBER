package ru.SberTex.SastManager.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.service.ProjectService;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ObjectMapper objectMapper;
    private final ProjectService projectService;

    @KafkaListener(topics = "topic-manager", groupId = "my-group")
    public void listen(String message) {
        try {
            ProjectOutDto projectDto = objectMapper.readValue(message, ProjectOutDto.class);
            projectService.saveUsersProject(projectDto);
        } catch (Exception e) {
            System.out.println("------------------------------------------");
            log.error(Arrays.toString(e.getStackTrace()));
            System.out.println("------------------------------------------");
            throw new RuntimeException("RuntimeException: " + e.getMessage());
        }
    }
}
