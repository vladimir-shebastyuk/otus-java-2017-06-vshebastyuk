package ru.otus.frontend.web.websocket;


import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.frontend.web.websocket.messages.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws")
public class WebSocketConnection {
    private Session session;
    private FrontendConnectionId connectionId;
    private WsMessageHandler wsMessageDispatcher;

    private Runnable onCloseErrorHandler;

    public WebSocketConnection(FrontendConnectionId connectionId, WsMessageHandler wsMessageDispatcher) {
        this.connectionId = connectionId;
        this.wsMessageDispatcher = wsMessageDispatcher;
    }

    public void registerOnClose(Runnable handler){
        this.onCloseErrorHandler = handler;
    }

    public FrontendConnectionId getConnectionId() {
        return connectionId;
    }

    @OnMessage
    public void onMessage(String data) throws IOException {
        try {
            WsMessage wsMessage = WsMessage.fromJson(data);

            try {
                this.wsMessageDispatcher.handle(wsMessage, this.getConnectionId());
            }catch (UnknownCommand unknownCommand) {
                this.getSession().getBasicRemote().sendText(
                        (new WsErrorResponse(
                                wsMessage,
                                WsErrorResponse.Reason.UNKNOWN_COMMAND,
                                unknownCommand.getMessage()
                        )).toString()
                );
            } catch (UnknownMessageType unknownMessageType) {
                this.getSession().getBasicRemote().sendText(
                        (new WsErrorResponse(
                                wsMessage,
                                WsErrorResponse.Reason.UNKNOWN_MESSAGE_TYPE,
                                unknownMessageType.getMessage()
                        )).toString()
                );
            }
        } catch (InvalidMessageException e) {
            this.getSession().getBasicRemote().sendText(
                    (new WsErrorResponse(WsErrorResponse.Reason.BAD_FORMAT)).toString()
            );
        }
    }

    public void sendMessage(String data){
        this.getSession().getAsyncRemote().sendText(data);
    }

    @OnOpen
    public void onOpen(Session session) {
        setSession(session);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose(CloseReason close) {
        if(onCloseErrorHandler!=null){
            try {
                onCloseErrorHandler.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable cause){
        if(onCloseErrorHandler!=null){
            try {
                onCloseErrorHandler.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
