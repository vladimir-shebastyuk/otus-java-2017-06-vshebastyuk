package ru.otus.frontend.web.websocket;

import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.frontend.web.websocket.commandhandler.CommandHandler;
import ru.otus.frontend.web.websocket.messages.UnknownCommand;
import ru.otus.frontend.web.websocket.messages.UnknownMessageType;
import ru.otus.frontend.web.websocket.messages.WsMessage;

/**
 *
 */
public class WsMessageHandler {
    private static final String COMMAND_TYPE = "command";
    private CommandHandler commandHandler;

    public WsMessageHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void handle(WsMessage message, FrontendConnectionId frontendConnectionId) throws UnknownCommand, UnknownMessageType {
        //в зависимости от типа сообщения отправляем на соответствующий обработчик
        //в данный момент поддерживаются только сообщения типа "command'
        if(COMMAND_TYPE.equals(message.getType())){
            this.commandHandler.handle(message,frontendConnectionId);
        }else{
            if(message.getType() != null) {
                throw new UnknownMessageType(message.getType());
            }else{
                throw new UnknownMessageType();
            }
        }
    }

}
