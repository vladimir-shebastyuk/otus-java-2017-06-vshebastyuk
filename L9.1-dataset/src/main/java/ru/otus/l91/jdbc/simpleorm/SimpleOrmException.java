package ru.otus.l91.jdbc.simpleorm;

/**
 * Ошибки экзекьютора
 */
public class SimpleOrmException extends Exception {
    public SimpleOrmException() {
    }

    public SimpleOrmException(String message) {
        super(message);
    }

    public SimpleOrmException(String message, Throwable cause) {
        super(message, cause);
    }
}
