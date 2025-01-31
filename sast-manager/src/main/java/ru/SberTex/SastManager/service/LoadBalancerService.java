package ru.SberTex.SastManager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Service
@RequiredArgsConstructor
@Slf4j
public class LoadBalancerService {
    private final StringRedisTemplate redisTemplate;

    private static final String MANAGER_LOAD_KEY = "sast-manager-load";
    private static final String METRICS_URL = "http://localhost:8080/actuator/metrics/http.server.requests";

    @Scheduled(fixedRate = 5000)
    public void updateLoad() {
        try {
            String instanceId = System.getenv("MANAGER_ID");
            RestTemplate restTemplate = new RestTemplate();
            String metricsJson = restTemplate.getForObject(METRICS_URL, String.class);
            int activeRequests = parseActiveRequests(metricsJson);
            redisTemplate.opsForHash().put(MANAGER_LOAD_KEY, instanceId, String.valueOf(activeRequests));
            redisTemplate.expire(MANAGER_LOAD_KEY, Duration.ofMinutes(1));

        } catch (Exception e) {
            log.error("Ошибка при обновлении нагрузки: {}", e.getMessage());
        }
    }

    private int parseActiveRequests(String metricsJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(metricsJson);
            JsonNode measurements = rootNode.path("measurements");

            if (measurements.isArray() && !measurements.isEmpty()) {
                JsonNode firstMeasurement = measurements.get(0);
                return firstMeasurement.path("value").asInt();
            }
        } catch (Exception e) {
            log.error("Ошибка парсинга метрик: {}", e.getMessage());
        }
        return 0;
    }


    public String getLeastLoadedManager() {
        return redisTemplate.opsForHash().entries(MANAGER_LOAD_KEY)
                .entrySet()
                .stream()
                .min((e1, e2) -> Integer.compare(
                        Integer.parseInt(e1.getValue().toString()),
                        Integer.parseInt(e2.getValue().toString())
                ))
                .map(e -> e.getKey().toString())
                .orElse("sast-manager-1");
    }
}
