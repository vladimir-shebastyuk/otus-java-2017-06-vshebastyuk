package ru.otus.frontend.messages;


import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.common.messagesystem.messages.MsgToFrontendService;
import ru.otus.frontend.web.websocket.messages.FluentMap;
import ru.otus.common.services.frontend.FrontendService;
import ru.otus.frontend.web.websocket.messages.WsMessage;

/**
 *
 */
public class MsgGetCacheStatsAnswer extends MsgToFrontendService {
    private final int hit;
    private final int miss;
    private final FrontendConnectionId connectionId;
    private final Long frontendMessageId;

    public MsgGetCacheStatsAnswer(Address from, Address to, FrontendConnectionId connectionId, Long frontendMessageId, int hit, int miss) {
        super(from, to, connectionId);
        this.hit = hit;
        this.miss = miss;
        this.connectionId = connectionId;
        this.frontendMessageId = frontendMessageId;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendToClient(
            this.connectionId,
                new WsMessage()
                        .setType(WsMessage.Type.Result)
                        .setOk(true)
                        .setReplyTo(this.frontendMessageId)
                        .setData( new FluentMap()
                                    .add("hit",this.hit)
                                    .add("miss",this.miss)
                                    .add("ratio",(double)this.hit / (double)(this.hit + this.miss) * 100)
                                )
                        .toString()
        );
    }
}
