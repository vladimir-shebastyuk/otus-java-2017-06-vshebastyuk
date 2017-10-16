package ru.otus.frontend.web.websocket;

import org.junit.Before;
import org.junit.Test;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.frontend.web.websocket.commandhandler.CommandHandler;
import ru.otus.frontend.web.websocket.messages.UnknownMessageType;
import ru.otus.frontend.web.websocket.messages.WsMessage;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 */
public class WsMessageHandlerTest {

    private CommandHandler commandHandler;
    private WsMessageHandler wsMessageHandler;

    @Before
    public void setUp() throws Exception {
        commandHandler = mock(CommandHandler.class);
        wsMessageHandler = new WsMessageHandler(commandHandler);
    }

    @Test
    public void handle_ok() throws Exception {
        WsMessage wsMessage = new WsMessage();
        wsMessage.setType(WsMessage.Type.Command);

        FrontendConnectionId frontendConnectionId = new FrontendConnectionId(1L);
        wsMessageHandler.handle(wsMessage, frontendConnectionId);

        verify(commandHandler).handle(wsMessage,frontendConnectionId);
    }

    @Test(expected = UnknownMessageType.class)
    public void handle_typeNotFound() throws Exception {
        WsMessage wsMessage = new WsMessage();
        wsMessage.setType("qwerty");

        FrontendConnectionId frontendConnectionId = new FrontendConnectionId(1L);
        wsMessageHandler.handle(wsMessage, frontendConnectionId);
    }

}