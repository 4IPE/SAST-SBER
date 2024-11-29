package ru.SberTex.SastAgent.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic topicManager() {
        return TopicBuilder.name("topic-agent")
                .partitions(3)  // Указываем количество разделов (partitions)
                .replicas(1)     // Указываем количество реплик для топика
                .build();        // Строим и возвращаем топик
    }
}
