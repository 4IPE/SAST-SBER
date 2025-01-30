package ru.SberTex.SastAgent.exception;

public class AnalyzeFailedException extends Exception {
    public AnalyzeFailedException() {
        super("Ошибка анализа проекта.");
    }
}
