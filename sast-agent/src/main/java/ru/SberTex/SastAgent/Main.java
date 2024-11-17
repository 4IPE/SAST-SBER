package ru.SberTex.SastAgent;

public class Main {
    public static void main(String[] args) {
        try {
            SASTAnalyzer analyzer = new SASTAnalyzer();
            analyzer.cloneRepository(1L, "https://github.com/DmitrySorokin111/BookStore.git");
            analyzer.buildProject(1L);
            analyzer.analyze(1L);

            analyzer.clearTempDirectory(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}