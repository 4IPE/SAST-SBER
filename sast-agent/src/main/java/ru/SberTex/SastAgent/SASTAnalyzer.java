package ru.SberTex.SastAgent;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;

public class SASTAnalyzer {

    private static final String DIR_TMP = "dir_tmp";

    public void cloneRepository(Long ProjectId, String repoUrl) throws GitAPIException {
        String filepath = DIR_TMP+"/"+ProjectId;
        File projDir = new File(filepath);
        if (projDir.exists()) {
            System.out.println("Repository already cloned");
            return;
        }

        System.out.println("Cloning: " + repoUrl);
        projDir.mkdirs();
        Git.cloneRepository()
                .setURI(repoUrl)
                .setDirectory(new File(filepath))
                .call()
                .close();
        System.out.println("Repository cloned successfully at " + filepath);
    }

    public void buildProject(Long ProjectId) {
        try {
//            File mvnw = new File(DIR_TMP+"/"+ProjectId+"/mvnw");
//
//            ProcessBuilder chmodProcess = new ProcessBuilder("chmod", "+x", mvnw.getAbsolutePath());
//            Process chmod = chmodProcess.start();
//            int code = chmod.waitFor();
//            System.out.println("code = "+code);

            ProcessBuilder processBuilder = new ProcessBuilder("mvn", "clean", "compile");

            processBuilder.directory(new File(DIR_TMP+"/"+ProjectId));
            System.out.println(DIR_TMP+"/"+ProjectId);

            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Build successful!");
            } else {
                System.out.println("Build failed!");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void clearTempDirectory(Long ProjectId) {
        String filepath = DIR_TMP + "/" + ProjectId;
        File projDir = new File(filepath);

        if (!projDir.exists()) {
            System.out.println("Temporary directory don't exists");
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
        System.out.println("Temporary directory cleared: " + filepath);
    }

    public void analyze(Long ProjectId) throws Exception {
        String filepath = DIR_TMP + "/" + ProjectId;

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

        System.out.println("SpotBugs analyze completed.");
    }

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
