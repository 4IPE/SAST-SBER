package ru.SberTex.SastAgent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.SberTex.SastAgent.SASTAnalyzer;
import ru.SberTex.SastDto.enumeration.Status;
import ru.SberTex.SastDto.model.ReportUpdateStatusDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyzerServiceImpl implements AnalyzerService {

    @Override
    public StringBuilder getReportContent(SASTAnalyzer analyzer) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(analyzer.getReportRelativePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void patchReportStatus(Long reportId, Status status) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ReportUpdateStatusDto upd = new ReportUpdateStatusDto(reportId, status);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(upd);
            log.info("Отправляемый JSON: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "http://manager-con:8080/report/updateStatus";

        HttpEntity<ReportUpdateStatusDto> entity = new HttpEntity<>(upd, headers);

        ResponseEntity<Void> response = template.exchange(url, HttpMethod.POST, entity, Void.class);
    }
}
