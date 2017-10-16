package ru.otus.frontend;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;
import ru.otus.common.services.frontend.FrontendService;
import ru.otus.frontend.web.filters.AuthenticationFilter;
import ru.otus.frontend.web.servlets.CacheMonitoringServlet;
import ru.otus.frontend.web.servlets.LoginServlet;
import ru.otus.frontend.web.servlets.MainServlet;
import ru.otus.common.messagesystem.Address;
import ru.otus.common.context.MessageSystemContext;
import ru.otus.frontend.web.websocket.WebSocketEndpoint;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import java.util.EnumSet;

/**
 *
 */
public class FrontendServiceImpl implements FrontendService {
    private final static String WWW_ROOT = "www";

    private final int port;
    private Address address;
    private MessageSystemContext context;

    private String webAdminLogin = "admin";
    private String webAdminPassword = "password";
    private WebSocketEndpoint webSocketEndpoint;

    public FrontendServiceImpl(int port, Address address, MessageSystemContext context) {
        this.port = port;

        this.address = address;
        this.context = context;
    }

    public void registerInMessageSystem(MessageSystemContext context, Address address){
        this.address = address;
        this.context = context;

        context.getMessageSystem().addAddressee(this);
    }

    public void start() throws Exception {
        Server server = this.getJettyServer();

        server.start();
        server.join();
    }

    private Server getJettyServer() throws ServletException, DeploymentException {
        Server server = new Server(this.port);

        WebAppContext root = new WebAppContext();

        root.setParentLoaderPriority(true);
        root.setContextPath("/");
        root.setResourceBase(FrontendService.class.getClassLoader().getResource(WWW_ROOT).toExternalForm());

        root.addServlet(new ServletHolder(new MainServlet()), "");
        root.addServlet(new ServletHolder(new CacheMonitoringServlet()), "/cache");
        root.addServlet(new ServletHolder(new LoginServlet(
                this.webAdminLogin,
                this.webAdminPassword
        )), "/login");

        //добавляем фильтр, для доступа только аутентифицированным пользователям
        root.addFilter(AuthenticationFilter.class,"/*", EnumSet.of(DispatcherType.REQUEST));

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[] { root });

        server.setHandler(handlerList);

        // Add javax.websocket support
        ServerContainer container = WebSocketServerContainerInitializer.configureContext(root);
        // Add echo endpoint to server container
        webSocketEndpoint = new WebSocketEndpoint(this.context);

        container.addEndpoint(webSocketEndpoint.getConfig());

        return server;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    public FrontendService setWebAdminLogin(String webAdminLogin) {
        this.webAdminLogin = webAdminLogin;

        return this;
    }

    public FrontendService setWebAdminPassword(String webAdminPassword) {
        this.webAdminPassword = webAdminPassword;

        return this;
    }

    @Override
    public void sendToClient(FrontendConnectionId connectionId, String message) {
        this.webSocketEndpoint.sendMessageToConnection(connectionId,message);
    }
}
