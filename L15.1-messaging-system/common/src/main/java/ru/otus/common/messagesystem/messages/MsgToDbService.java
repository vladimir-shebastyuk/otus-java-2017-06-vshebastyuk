package ru.otus.common.messagesystem.messages;


import ru.otus.common.services.db.DbService;
import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.messagesystem.Message;

/**
 *
 */
public abstract class MsgToDbService extends Message {
    public MsgToDbService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DbService) {
            exec((DbService) addressee);
        }
    }

    public abstract void exec(DbService dbService);
}
