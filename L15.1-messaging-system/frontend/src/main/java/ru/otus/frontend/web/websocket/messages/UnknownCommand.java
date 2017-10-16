package ru.otus.frontend.web.websocket.messages;

/**
 *
 */
public class UnknownCommand extends Exception {
    public UnknownCommand() {
        super("Unknown command");
    }

    public UnknownCommand(String command) {
        super("Unknown command '" + command + "'");
    }
}
