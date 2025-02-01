package ru.SberTex.SastAgent.exception;

public class BuildFailedException extends Exception {
    public BuildFailedException() {
        super("Ошибка сборки проекта. Проверьте pom.xml или настройки проекта.");
    }
}
