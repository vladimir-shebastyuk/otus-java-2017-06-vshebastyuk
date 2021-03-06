package ru.otus.l111;

import ru.otus.l111.cache.DbServiceCacheDecorator;
import ru.otus.l111.cache.engine.CacheEngineImpl;
import ru.otus.l111.datasets.AddressDataSet;
import ru.otus.l111.datasets.PhoneDataSet;
import ru.otus.l111.datasets.UserDataSet;
import ru.otus.l111.dbservice.DbService;
import ru.otus.l111.hibernate.DbServiceHibernateImpl;

/**
 * Домашная работа L11
 */
public class L11HwMain {
    public static void main(String... args) throws Exception {
        try (DbService dbService = new DbServiceCacheDecorator(
                new DbServiceHibernateImpl(
                        DbConfig.getInstance().getUrl(),
                        DbConfig.getInstance().getUser(),
                        DbConfig.getInstance().getPassword()
                ),
                ()-> new CacheEngineImpl(
                        10,100,0,false
                ))
        ) {
            UserDataSet peterParker = new UserDataSet("Peter Parker", 18);
            System.out.println("Saving... " + peterParker.toString());
            dbService.saveUser(peterParker);
            System.out.println("Saved: " + peterParker.toString());

            long peterParkerId = peterParker.getId();
            peterParker.setAge(19);

            System.out.println("Change user: " + peterParker.toString());

            dbService.saveUser(peterParker);

            UserDataSet newPeterParker = dbService.loadUser(peterParkerId);

            System.out.println("Loaded: " + newPeterParker.toString());

            System.out.println("Add address and phones: " + peterParker.toString());

            newPeterParker.setAddress(new AddressDataSet("20 Ingram Street in Forest Hills, Queens"));
            newPeterParker.addPhone(new PhoneDataSet("+1-555-NO-ONE-KNOWS"));
            newPeterParker.addPhone(new PhoneDataSet("+1-555-FOR-MARRY-JANE"));

            dbService.saveUser(newPeterParker);

            newPeterParker = dbService.loadUser(peterParkerId);

            System.out.println("Loaded with all info: " + newPeterParker.toString());
        }
    }
}
