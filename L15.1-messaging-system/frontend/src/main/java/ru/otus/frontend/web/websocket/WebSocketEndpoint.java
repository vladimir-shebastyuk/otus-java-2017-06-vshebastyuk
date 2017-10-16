package ru.otus.frontend.web.websocket;

import com.google.gson.Gson;
import ru.otus.common.context.MessageSystemContext;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.frontend.web.websocket.messages.FluentMap;
import ru.otus.frontend.web.websocket.commandhandler.CommandHandler;
import ru.otus.frontend.web.websocket.messages.WsMessage;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Корневой класс для управления подключениям
 */
public class WebSocketEndpoint  {
    private AtomicLong connectionIdGenerator = new AtomicLong(1L);
    private final Map<FrontendConnectionId,WebSocketConnection> connections;
    private MessageSystemContext context;
    private CommandHandler commandHandler;
    private WsMessageHandler wsMessageDispatcher;

    public WebSocketEndpoint(MessageSystemContext context) {
        this.context = context;
        this.connections = new ConcurrentHashMap<FrontendConnectionId,WebSocketConnection>();
        this.commandHandler = new CommandHandler(context);
        this.wsMessageDispatcher = new WsMessageHandler(this.commandHandler);
    }

    protected WebSocketConnection createNewConnection(){
        FrontendConnectionId frontendConnectionId = getNewConnectionId();

        WebSocketConnection connection = new WebSocketConnection(
                frontendConnectionId,
                this.wsMessageDispatcher
        );

        connection.registerOnClose(()->{this.removeConnection(frontendConnectionId);});

        this.connections.put(frontendConnectionId,connection);

        return connection;
    }

    private FrontendConnectionId getNewConnectionId() {
        return new FrontendConnectionId(connectionIdGenerator.getAndIncrement());
    }

    protected void removeConnection(FrontendConnectionId frontendConnectionId){
        this.connections.remove(frontendConnectionId);
    }

    public ServerEndpointConfig getConfig(){
        return ServerEndpointConfig.Builder.create(
                WebSocketConnection.class,
                WebSocketConnection.class.getAnnotation(ServerEndpoint.class).value()
        )
                .configurator(new ServerEndpointConfig.Configurator() {
                    @Override
                    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
                        //noinspection unchecked
                        return (T) WebSocketEndpoint.this.createNewConnection();
                    }
                })
                .build();
    }

    public void sendMessageToConnection(FrontendConnectionId connectionId, String message){
        WebSocketConnection connection = this.connections.get(connectionId);

        if(connection != null){
            connection.sendMessage(message);
        }
    }
}
