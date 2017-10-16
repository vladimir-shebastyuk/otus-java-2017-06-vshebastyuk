package ru.otus.main;


import ru.otus.backend.cache.CacheServiceImpl;
import ru.otus.backend.cache.DbServiceCacheDecorator;
import ru.otus.backend.hibernate.DbServiceHibernateImpl;
import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.MessageSystem;
import ru.otus.common.context.MessageSystemContext;
import ru.otus.common.services.cache.CacheService;
import ru.otus.common.services.db.DbService;
import ru.otus.common.services.frontend.FrontendService;
import ru.otus.frontend.FrontendServiceImpl;

/**
 * Для запуска см. README.md
 */
public class ApplicationMain {
    private final static int PORT = 8088;

    public static void main(String... args) throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext context = new MessageSystemContext(messageSystem);

        Address frontAddress = new Address("FrontendService");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);
        Address cacheServiceAddress = new Address("CacheService");
        context.setCacheServiceAddress(cacheServiceAddress);

        /* Создаем кэш сервис и регистрируем его */
        CacheServiceImpl cacheService = new CacheServiceImpl(1000,10000,0,false);
        cacheService.registerInMessageSystem(context,cacheServiceAddress);

        /* Создаем БД сервис и регистрируем его */
        try(DbServiceCacheDecorator dbService = createCachedDbService(cacheService)) {
            dbService.registerInMessageSystem(context, dbAddress);

            //запускаем имитатор бурной деятельности над DbService'ом в отдельном потоке
            startDbServiceHardWorker(dbService);


            FrontendServiceImpl frontendService = new FrontendServiceImpl(8080,context.getFrontAddress(),context);
            context.getMessageSystem().addAddressee(frontendService);

            messageSystem.start();

            frontendService.start();
        }
    }

    private static void startDbServiceHardWorker(DbService dbService) {
        Thread thread = new Thread(new HardWorker(dbService));
        thread.setDaemon(true);
        thread.start();
    }


    private static DbServiceCacheDecorator createCachedDbService(CacheService cacheService){
        return new DbServiceCacheDecorator(
                new DbServiceHibernateImpl(
                        ru.otus.main.ApplicationConfig.getInstance().getDbUrl(),
                        ru.otus.main.ApplicationConfig.getInstance().getDbUser(),
                        ru.otus.main.ApplicationConfig.getInstance().getDbPassword()
                ),
                cacheService
        );
    }
}
