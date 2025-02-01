package ru.SberTex.SastAgent;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import edu.umd.cs.findbugs.*;
import edu.umd.cs.findbugs.config.UserPreferences;
import ru.SberTex.SastAgent.exception.AnalyzeFailedException;
import ru.SberTex.SastAgent.exception.BuildFailedException;
import ru.SberTex.SastAgent.exception.CloningRepoException;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для анализа проектов с использованием SpotBugs.
 * <p>
 * Этот класс предоставляет методы для клонирования репозитория, сборки проекта,
 * анализа кода с помощью SpotBugs и очистки временных директорий.
 * </p>
 */
@Slf4j
public class SASTAnalyzer {

    private static final String DIR_TMP = "dir_tmp"; // Временная директория для хранения клонированных репозиториев
    private Long projectId; // Идентификатор проекта
    private String url; // URL репозитория
    private String branch; // название ветки

    /**
     * Конструктор для создания экземпляра SASTAnalyzer.
     *
     * @param ProjectId идентификатор проекта
     * @param repoUrl   URL репозитория для клонирования
     */
    public SASTAnalyzer(Long ProjectId, String repoUrl) {
        projectId = ProjectId;
        url = repoUrl;
        branch = "main";
    }

    /**
     * Конструктор для создания экземпляра SASTAnalyzer.
     *
     * @param ProjectId     идентификатор проекта
     * @param repoUrl       URL репозитория для клонирования
     * @param branchName    название ветки
     */
    public SASTAnalyzer(Long ProjectId, String repoUrl, String branchName) {
        projectId = ProjectId;
        url = repoUrl;
        branch = branchName;
    }

    /**
     * Клонирует репозиторий по указанному URL.
     *
     * @throws CloningRepoException если произошла ошибка при клонировании репозитория
     */
    public void cloneRepository() throws CloningRepoException {
        String filepath = DIR_TMP+"/"+projectId;
        File projDir = new File(filepath);
        if (projDir.exists()) {
            log.info("Repository already cloned");
            return;
        }
        try {
            log.info("Cloning: " + url);
            projDir.mkdirs();
            Git.cloneRepository()
                    .setURI(url)
                    .setBranch(branch)
                    .setDirectory(new File(filepath))
                    .call()
                    .close();
            log.info("Repository cloned successfully at " + filepath);
        } catch (Exception e) {
            throw new CloningRepoException();
        }
    }

    /**
     * Собирает проект с использованием Maven.
     *
     * @throws BuildFailedException если произошла ошибка при сборке проекта
     */
    public void buildProject() throws BuildFailedException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("mvn", "clean", "compile");

            processBuilder.directory(new File(DIR_TMP+"/"+projectId));
            log.info(DIR_TMP+"/"+projectId);

            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Build successful!");
            } else {
                log.error("Build failed!");
                throw new BuildFailedException();
            }

        } catch (Exception e) {
            throw new BuildFailedException();
        }
    }

    /**
     * Очищает временную директорию, удаляя все файлы и подкаталоги.
     */
    public void clearTempDirectory() {
        String filepath = DIR_TMP + "/" + projectId;
        File projDir = new File(filepath);

        if (!projDir.exists()) {
            log.info("Temporary directory don't exists");
            return;
        }

        File[] files = projDir.listFiles();
        if (files == null) {
            projDir.delete();
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                clearDirectory(file);
            }
            file.delete();
        }

        projDir.delete();
        log.info("Temporary directory cleared: " + filepath);
    }

    /**
     * Выполняет анализ кода с использованием SpotBugs.
     *
     * @throws AnalyzeFailedException если произошла ошибка во время анализа
     */
    public void analyze2() throws AnalyzeFailedException {
        String filepath = DIR_TMP + "/" + projectId;
        try {
            Project project = new Project();
            project.addFile(filepath);

            UserPreferences userPreferences = UserPreferences.createDefaultUserPreferences();

            HTMLBugReporter bugReporter = new HTMLBugReporter(project, "styles/custom.xsl");
            bugReporter.setPriorityThreshold(Priorities.NORMAL_PRIORITY);
            bugReporter.setOutputStream(new PrintStream(filepath+"/spotbugs-report.html"));

            FindBugs2 findBugs = new FindBugs2();
            findBugs.setUserPreferences(userPreferences);
            findBugs.setBugReporter(bugReporter);
            findBugs.setProject(project);
            findBugs.setDetectorFactoryCollection(DetectorFactoryCollection.instance());

            findBugs.execute();
        } catch (Exception e) {
            throw new AnalyzeFailedException();
        }
        log.info("SpotBugs analyze completed.");
    }

    /**
     * Возвращает относительный путь к отчету SpotBugs.
     *
     * @return относительный путь к отчету
     */
    public String getReportRelativePath() {
        return DIR_TMP+"/"+projectId+"/spotbugs-report.html";
    }

    // Пустой конструктор для предотвращения создания экземпляров без параметров
    private SASTAnalyzer() {}

    /**
     * Рекурсивно очищает указанную директорию, удаляя все файлы и подкаталоги.
     *
     * @param directory директория, которую необходимо очистить
     */
    private static void clearDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    clearDirectory(file);
                }
                file.delete();
            }
        }
        directory.delete();
    }
}
