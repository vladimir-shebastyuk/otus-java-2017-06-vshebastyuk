package ru.otus.l91;


import ru.otus.l91.datasets.UserDataSet;
import ru.otus.l91.dbservice.DbService;
import ru.otus.l91.jdbc.DbServiceJdbcImpl;

import java.util.*;

/**
 * Домашнаяя работы L9 и первая часть L10
 */
public class L91HwMain {

    public static void main(String... args) throws Exception{
        DbService dbService = new DbServiceJdbcImpl(
                DbConfig.getInstance().getUrl(),
                DbConfig.getInstance().getUser(),
                DbConfig.getInstance().getPassword()
        );

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
    }
}
