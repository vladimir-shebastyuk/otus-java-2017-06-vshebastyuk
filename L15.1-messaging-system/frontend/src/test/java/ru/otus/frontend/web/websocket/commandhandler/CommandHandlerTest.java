package ru.otus.frontend.web.websocket.commandhandler;

import org.junit.Before;
import org.junit.Test;
import ru.otus.common.context.MessageSystemContext;
import ru.otus.common.messagesystem.Message;
import ru.otus.common.messagesystem.MessageSystem;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.frontend.messages.MsgGetCacheStats;
import ru.otus.frontend.web.websocket.messages.FluentMap;
import ru.otus.frontend.web.websocket.messages.UnknownCommand;
import ru.otus.frontend.web.websocket.messages.WsMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 */
public class CommandHandlerTest {
    private MessageSystemContext context;
    private MessageSystem messageSystem;

    @Before
    public void setUp() throws Exception {
        context = mock(MessageSystemContext.class);
        messageSystem = mock(MessageSystem.class);

        when(context.getMessageSystem()).thenReturn(messageSystem);
    }

    @Test
    public void handle_ok() throws Exception {
        WsMessage wsMessage = new WsMessage().setData(
                new FluentMap().add("name", "GetCacheStats")
        );

        FrontendConnectionId frontendConnectionId = new FrontendConnectionId(1L);

        CommandHandler commandHandler = new CommandHandler(context);

        commandHandler.handle(wsMessage,frontendConnectionId);

        verify(messageSystem,times(1)).sendMessage(any(Message.class));
    }

    @Test(expected = UnknownCommand.class)
    public void handle_unknownCommand() throws Exception {
        WsMessage wsMessage = new WsMessage().setData(
                new FluentMap().add("name", "qwety")
        );

        FrontendConnectionId frontendConnectionId = new FrontendConnectionId(1L);

        CommandHandler commandHandler = new CommandHandler(context);

        commandHandler.handle(wsMessage,frontendConnectionId);
    }

}