package ru.SberTex.SastAgent.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ProjectDto;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "topic-agent", groupId = "my-group")
    public void listen(String message) {
        try {
            ProjectDto projectDto = objectMapper.readValue(message, ProjectDto.class);
        }catch (Exception e){
            System.out.println("------------------------------------------");
            log.error(Arrays.toString(e.getStackTrace()));
            System.out.println("------------------------------------------");
            throw new RuntimeException("RuntimeException: "+e.getMessage());
        }
    }
}
