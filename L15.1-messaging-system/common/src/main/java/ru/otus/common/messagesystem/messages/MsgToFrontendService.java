package ru.otus.common.messagesystem.messages;



import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.messagesystem.Message;
import ru.otus.common.services.frontend.FrontendService;

/**
 *
 */
public abstract class MsgToFrontendService extends Message {
    private FrontendConnectionId connectionId;

    public MsgToFrontendService(Address from, Address to, FrontendConnectionId connectionId) {
        super(from, to);
        this.connectionId = connectionId;
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService dbService);
}
