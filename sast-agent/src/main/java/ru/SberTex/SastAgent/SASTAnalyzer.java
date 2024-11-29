package ru.SberTex.SastAgent;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;

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

    /**
     * Конструктор для создания экземпляра SASTAnalyzer.
     *
     * @param ProjectId идентификатор проекта
     * @param repoUrl   URL репозитория для клонирования
     */
    public SASTAnalyzer(Long ProjectId, String repoUrl) {
        projectId = ProjectId;
        url = repoUrl;
    }

    /**
     * Клонирует репозиторий по указанному URL.
     *
     * @throws GitAPIException если произошла ошибка при клонировании репозитория
     */
    public void cloneRepository() throws GitAPIException {
        String filepath = DIR_TMP+"/"+projectId;
        File projDir = new File(filepath);
        if (projDir.exists()) {
            log.info("Repository already cloned");
            return;
        }

        log.info("Cloning: " + url);
        projDir.mkdirs();
        Git.cloneRepository()
                .setURI(url)
                .setDirectory(new File(filepath))
                .call()
                .close();
        System.out.println("Repository cloned successfully at " + filepath);
    }

    /**
     * Собирает проект с использованием Maven.
     */
    public void buildProject() {
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
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
     * @throws Exception если произошла ошибка во время анализа
     */
    public void analyze() throws Exception {
        String filepath = DIR_TMP + "/" + projectId;

        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-jar", "/spotbugs/lib/spotbugs.jar",
                "-textui", "-html", "-output", filepath + "/spotbugs-report.html", filepath
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("SpotBugs stopped with error, error code: " + exitCode);
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
