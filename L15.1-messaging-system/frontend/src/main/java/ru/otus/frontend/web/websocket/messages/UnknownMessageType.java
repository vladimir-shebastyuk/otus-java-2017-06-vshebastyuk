package ru.otus.frontend.web.websocket.messages;

/**
 *
 */
public class UnknownMessageType extends Exception {
    public UnknownMessageType() {
        super("Unknown message type");
    }

    public UnknownMessageType(String command) {
        super("Unknown message type '" + command + "'");
    }
}
