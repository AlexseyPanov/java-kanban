package ru.yandex.app.exception;

public class ManagerLoadException extends RuntimeException {
    public ManagerLoadException() {
    }

    public ManagerLoadException(String message) {
        super(message);
    }

    public ManagerLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
