package ru.otus.l51.testframework;

/**
 * Ошибка при проверке в тестах
 */
public class AssertionError extends Error {
    public AssertionError() {
    }

    public AssertionError(String message) {
        super(message);
    }

    public AssertionError(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertionError(Throwable cause){
        super(cause);
    }
}
