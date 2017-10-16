package ru.otus.frontend.web.websocket.commandhandler;

import ru.otus.common.context.MessageSystemContext;
import ru.otus.common.messagesystem.Message;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.frontend.messages.MsgGetCacheStats;
import ru.otus.frontend.web.websocket.messages.UnknownCommand;
import ru.otus.frontend.web.websocket.messages.WsMessage;

import java.util.HashMap;

/**
 * Обработкич вызова команд (сообщений с type=command) с фронтенда
 */
public class CommandHandler {
    private static final String COMMAND_NAME_FIELD = "name";
    private MessageSystemContext messageSystemContext;


    public CommandHandler(MessageSystemContext messageSystemContext) {
        this.messageSystemContext = messageSystemContext;
    }

    public void handle(WsMessage wsMessage, FrontendConnectionId frontendConnectionId) throws UnknownCommand {
        //TODO: По мере добавления команд имеет смысл добавить аннотации на классы команд и реализовать их вызов через reflection
        if("GetCacheStats".equals(wsMessage.getData().get(COMMAND_NAME_FIELD))){
            Message message = new MsgGetCacheStats(
                this.messageSystemContext.getMessageSystem(),
                this.messageSystemContext.getFrontAddress(),
                this.messageSystemContext.getCacheAddress(),
                frontendConnectionId,
                wsMessage.getId()
            );

            this.messageSystemContext.getMessageSystem().sendMessage(message);
        }else{
            throw new UnknownCommand((String)wsMessage.getData().get(COMMAND_NAME_FIELD));
        }
    }
}
