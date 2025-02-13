import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.SberTex.SastAgent.SASTAnalyzer;
import ru.SberTex.SastAgent.service.AnalyzerServiceImpl;
import ru.SberTex.SastDto.enumeration.Status;
import ru.SberTex.SastDto.model.ReportUpdateStatusDto;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyzerServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AnalyzerServiceImpl analyzerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReportContent_ShouldReturnContent_WhenFileExists() throws IOException {
        // Arrange
        SASTAnalyzer analyzer = mock(SASTAnalyzer.class);
        when(analyzer.getReportRelativePath()).thenReturn("src/test/resources/test-report.txt");

        // Act
        StringBuilder content = analyzerService.getReportContent(analyzer);

        // Assert
        assertNotNull(content);
        assertTrue(content.length() > 0);
    }

    @Test
    void getReportContent_ShouldHandleIOException_WhenFileDoesNotExist() throws IOException {
        // Arrange
        SASTAnalyzer analyzer = mock(SASTAnalyzer.class);
        when(analyzer.getReportRelativePath()).thenReturn("non-existent-file.txt");

        // Act
        StringBuilder content = analyzerService.getReportContent(analyzer);

        // Assert
        assertNotNull(content);
        assertEquals(0, content.length());
    }

    @Test
    void patchReportStatus_ShouldSendPostRequest_WhenStatusIsUpdated() {
        // Arrange
        Long reportId = 1L;
        Status status = Status.DONE;
        ReportUpdateStatusDto upd = new ReportUpdateStatusDto(reportId, status);

        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock); // Возвращаем мок для opsForValue()
        when(valueOperationsMock.get("least-loaded-manager")).thenReturn("sast-manager-2"); // Настраиваем поведение get()

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReportUpdateStatusDto> entity = new HttpEntity<>(upd, headers);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.eq("http://sast-manager-2:8080/report/updateStatus"),
                ArgumentMatchers.eq(HttpMethod.POST),
                any(HttpEntity.class),
                ArgumentMatchers.eq(Void.class))
        ).thenReturn(responseEntity);

        // Act
        analyzerService.patchReportStatus(reportId, status);

        // Assert
        verify(restTemplate, times(1)).exchange(
                eq("http://sast-manager-2:8080/report/updateStatus"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Void.class));
    }

    @Test
    void patchReportStatus_ShouldUseDefaultManager_WhenRedisReturnsNull() {
        // Arrange
        Long reportId = 1L;
        Status status = Status.DONE;
        ReportUpdateStatusDto upd = new ReportUpdateStatusDto(reportId, status);

        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(valueOperationsMock.get("least-loaded-manager")).thenReturn(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReportUpdateStatusDto> entity = new HttpEntity<>(upd, headers);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(
                        eq("http://sast-manager-1:8080/report/updateStatus"),
                        eq(HttpMethod.POST),
                        eq(entity),
                        eq(Void.class)))
                .thenReturn(responseEntity);

        // Act
        analyzerService.patchReportStatus(reportId, status);

        // Assert
        verify(restTemplate, times(1)).exchange(
                eq("http://sast-manager-1:8080/report/updateStatus"),
                eq(HttpMethod.POST),
                eq(entity),
                eq(Void.class));
    }
}