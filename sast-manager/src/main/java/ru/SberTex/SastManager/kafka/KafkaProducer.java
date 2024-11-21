package ru.SberTex.SastManager.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;

import java.util.Arrays;

@Service
@EnableKafka
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final static String TOPIC = "topic-agent";


    public void sendMessageInAgent(ProjectDto projectDto) {
        try {
            String message = objectMapper.writeValueAsString(projectDto);
            kafkaTemplate.send(TOPIC, message);
        } catch (Exception e) {
            System.out.println("------------------------------------------");
            log.error(Arrays.toString(e.getStackTrace()));
            System.out.println("------------------------------------------");
            throw new RuntimeException("RuntimeException: " + e.getMessage());
        }
    }
}