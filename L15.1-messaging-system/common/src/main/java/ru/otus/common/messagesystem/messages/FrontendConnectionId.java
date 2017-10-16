package ru.otus.common.messagesystem.messages;

import java.util.Objects;

/**
 * Идентификатор подключения на стороне фронтэнда
 */
public class FrontendConnectionId {
    private long connectionId;

    public FrontendConnectionId(long connectionId) {
        this.connectionId = connectionId;
    }

    public long getConnectionId() {
        return connectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontendConnectionId that = (FrontendConnectionId) o;
        return connectionId == that.connectionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionId);
    }
}
