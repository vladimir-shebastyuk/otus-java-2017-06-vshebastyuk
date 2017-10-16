package ru.otus.common.messagesystem.messages;



import ru.otus.common.services.cache.CacheService;
import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.messagesystem.Message;

/**
 * 
 */
public abstract class MsgToCacheService extends Message {
    public MsgToCacheService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CacheService) {
            exec((CacheService) addressee);
        }
    }

    public abstract void exec(CacheService dbService);
}
