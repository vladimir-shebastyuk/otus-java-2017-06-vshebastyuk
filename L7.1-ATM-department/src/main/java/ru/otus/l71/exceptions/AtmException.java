package ru.otus.l71.exceptions;

/**
 * Ошибки ATM
 */
public class AtmException extends Exception {
    public AtmException() {
    }

    public AtmException(String message) {
        super(message);
    }
}
