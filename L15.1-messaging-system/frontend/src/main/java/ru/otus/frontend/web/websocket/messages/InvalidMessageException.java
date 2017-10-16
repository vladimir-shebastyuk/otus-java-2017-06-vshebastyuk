package ru.otus.frontend.web.websocket.messages;

/**
 *
 */
public class InvalidMessageException extends Exception {

    public InvalidMessageException() {
    }

    public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
