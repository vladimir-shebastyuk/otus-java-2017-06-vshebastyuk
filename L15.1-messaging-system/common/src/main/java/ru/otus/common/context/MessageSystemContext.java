package ru.otus.common.context;


import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.MessageSystem;

/**
 *
 */
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress;
    private Address dbAddress;
    private Address cacheAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    public Address getCacheAddress() {
        return cacheAddress;
    }

    public void setCacheServiceAddress(Address cacheAddress) {
        this.cacheAddress = cacheAddress;
    }
}
