package ru.SberTex.SastAgent.exception;

public class CloningRepoException extends Exception {
    public CloningRepoException() {
        super("Ошибка клонирования проекта. Проверьте настройки удалённого репозитория и наличие ветки main.");
    }
}
