package ru.otus.l121;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import ru.otus.l121.cache.CacheMonitoring;
import ru.otus.l121.cache.DbServiceCacheDecorator;
import ru.otus.l121.cache.engine.CacheEngineImpl;
import ru.otus.l121.hibernate.DbServiceHibernateImpl;
import ru.otus.l121.web.filters.AuthenticationFilter;
import ru.otus.l121.web.servlets.CacheMonitoringServlet;
import ru.otus.l121.web.servlets.LoginServlet;
import ru.otus.l121.web.servlets.MainServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Для запуска см. README.md
 */
public class L12HwMain {
    private final static String WWW_ROOT = "www";
    private final static int PORT = 8088;

    public static void main(String... args) throws Exception {
        try(DbServiceCacheDecorator dbService = createCachedDbService()) {
            Server server = getJettyServer(dbService);

            //запускаем имитатор бурной деятельности над DbService'ом в отдельном потоке
            new Thread(new HardWorker(dbService)).start();

            //стартуем сервер
            server.start();
            server.join();
        }
    }

    private static Server getJettyServer(CacheMonitoring cacheMonitoring) {
        WebAppContext root = new WebAppContext();

        root.setParentLoaderPriority(true);
        root.setContextPath("/");
        root.setResourceBase(L12HwMain.class.getClassLoader().getResource(WWW_ROOT).toExternalForm());

        root.addServlet(new ServletHolder(new MainServlet()), "");
        root.addServlet(new ServletHolder(new CacheMonitoringServlet(cacheMonitoring)), "/cache");
        root.addServlet(new ServletHolder(new LoginServlet(
                ApplicationConfig.getInstance().getWebAdmin(),
                ApplicationConfig.getInstance().getWebPassword()
        )), "/login");

        //добавляем фильтр, для доступа только аутентифицированным пользователям
        root.addFilter(AuthenticationFilter.class,"/*", EnumSet.of(DispatcherType.REQUEST));

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[] { root });

        Server server = new Server(PORT);
        server.setHandler(handlerList);
        return server;
    }

    private static DbServiceCacheDecorator createCachedDbService(){
        return new DbServiceCacheDecorator(
                new DbServiceHibernateImpl(
                        ApplicationConfig.getInstance().getDbUrl(),
                        ApplicationConfig.getInstance().getDbUser(),
                        ApplicationConfig.getInstance().getDbPassword()
                ),
                ()-> new CacheEngineImpl(
                        100,10000,0,false
                ));
    }
}
