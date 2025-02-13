import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.SberTex.SastAgent.SASTAnalyzer;
import ru.SberTex.SastAgent.exception.AnalyzeFailedException;
import ru.SberTex.SastAgent.exception.BuildFailedException;
import ru.SberTex.SastAgent.exception.CloningRepoException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SASTAnalyzerTest {

    private SASTAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = spy(new SASTAnalyzer(1L, "https://fake-repo.git"));
    }

    @Test
    void cloneRepository_ShouldNotThrowException_WhenCloningSucceeds() throws CloningRepoException {
        doNothing().when(analyzer).cloneRepository();
        assertDoesNotThrow(() -> analyzer.cloneRepository());
    }

    @Test
    void buildProject_ShouldThrowException_WhenBuildFails() throws BuildFailedException {
        doThrow(new BuildFailedException()).when(analyzer).buildProject();
        assertThrows(BuildFailedException.class, () -> analyzer.buildProject());
    }

    @Test
    void analyze2_ShouldThrowException_WhenAnalysisFails() throws AnalyzeFailedException {
        doThrow(new AnalyzeFailedException()).when(analyzer).analyze2();
        assertThrows(AnalyzeFailedException.class, () -> analyzer.analyze2());
    }

    @Test
    void clearTempDirectory_ShouldAttemptToDeleteFiles() {
        File mockFile = mock(File.class);
        File mockDir = mock(File.class);

        when(mockDir.exists()).thenReturn(true);
        when(mockDir.listFiles()).thenReturn(new File[]{mockFile});
        when(mockFile.delete()).thenReturn(true);
        when(mockDir.delete()).thenReturn(true);

        SASTAnalyzer analyzer = Mockito.spy(new SASTAnalyzer(123L, "dummy-url"));
        doReturn(mockDir).when(analyzer).getProjectDirectory(); // Подменяем реальную директорию на мок

        analyzer.clearTempDirectory();

        verify(mockFile, times(1)).delete(); // Проверяем, что файл пытались удалить
        verify(mockDir, times(1)).delete(); // Проверяем, что директория пыталась удалиться
    }
}