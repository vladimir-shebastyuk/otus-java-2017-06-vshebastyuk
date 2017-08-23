package ru.otus.l101.dbservice;

/**
 * Ошибка сервиса БД
 */
public class DbServiceException extends Exception{
    public DbServiceException() {
    }

    public DbServiceException(String message) {
        super(message);
    }

    public DbServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbServiceException(Throwable cause) {
        super(cause);
    }

    public DbServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
