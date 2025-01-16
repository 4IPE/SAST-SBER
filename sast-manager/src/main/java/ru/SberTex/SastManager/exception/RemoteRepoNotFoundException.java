package ru.SberTex.SastManager.exception;

public class RemoteRepoNotFoundException extends Exception {
    public RemoteRepoNotFoundException() {
        super("Удалённого репозитория не существует или он приватный");
    }
}
