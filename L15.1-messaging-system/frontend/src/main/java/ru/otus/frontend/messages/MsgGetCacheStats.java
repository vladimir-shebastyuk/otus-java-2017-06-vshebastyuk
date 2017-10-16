package ru.otus.frontend.messages;


import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.MessageSystem;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.common.messagesystem.messages.MsgToCacheService;
import ru.otus.common.services.cache.CacheService;

/**
 *
 */
public class MsgGetCacheStats extends MsgToCacheService {
    protected final MessageSystem messageSystem;
    protected final FrontendConnectionId connectionId;
    protected Long frontendMessageId;

    public MsgGetCacheStats(MessageSystem messageSystem, Address from, Address to, FrontendConnectionId connectionId, Long frontendMessageId) {
        super(from, to);
        this.messageSystem = messageSystem;
        this.connectionId = connectionId;
        this.frontendMessageId = frontendMessageId;
    }

    @Override
    public void exec(CacheService cacheService) {
        int hit = cacheService.getTotalHitCount();
        int miss = cacheService.getTotalMissCount();
        messageSystem.sendMessage(new MsgGetCacheStatsAnswer(getTo(), getFrom(), this.connectionId, this.frontendMessageId, hit,miss));
    }
}
