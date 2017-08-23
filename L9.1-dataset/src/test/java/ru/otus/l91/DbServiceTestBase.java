package ru.otus.l91;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l91.datasets.DataSet;
import ru.otus.l91.datasets.UserDataSet;
import ru.otus.l91.dbservice.DbService;
import ru.otus.l91.hibernate.DbServiceHibernateImpl;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public abstract class DbServiceTestBase {
    protected static final String JDBC_URL = "jdbc:h2:mem:test";
    protected static final String JDBC_INIT_URL = "jdbc:h2:mem:test;INIT=runscript from 'classpath:/scripts/create-users-table.sql'";

    protected Connection connection;
    protected DbService dbService;

    @Before
    public void startDb() throws Exception {
        connection = DriverManager.getConnection(JDBC_INIT_URL);
    }

    @After
    public void stopDb() throws Exception {
        this.connection.close();
        this.dbService.close();
    }

    @Test
    public void autoGenerateIdTest() throws Exception {
        UserDataSet userDataSet = new UserDataSet("test",15);
        this.dbService.saveUser(userDataSet);

        Assert.assertEquals(1L,userDataSet.getId());

        userDataSet = new UserDataSet("test2",18);
        this.dbService.saveUser(userDataSet);

        Assert.assertEquals(2L,userDataSet.getId());
    }

    @Test
    public void saveLoad() throws Exception {
        UserDataSet userDataSet = new UserDataSet("test",15);
        this.dbService.saveUser(userDataSet);

        long id = userDataSet.getId();

        DataSet loadedUserDataSet = this.dbService.loadUser(id);

        Assert.assertEquals(userDataSet,loadedUserDataSet);
    }

    @Test
    public void updateExiting() throws Exception {
        //1. создаем пользователя
        UserDataSet userDataSet = new UserDataSet("test",15);
        this.dbService.saveUser(userDataSet);

        long id = userDataSet.getId();

        //2. Изменяем его и снова сохраняем
        userDataSet.setAge(77);
        userDataSet.setName("New Name");

        this.dbService.saveUser(userDataSet);

        //3. проверяем, что id не поменялся
        Assert.assertEquals(id,userDataSet.getId());

        //4. Вычитываем из базы по id
        id = userDataSet.getId();

        UserDataSet loadedUserDataSet = this.dbService.loadUser(id);

        Assert.assertEquals("New Name",loadedUserDataSet.getName());
        Assert.assertEquals(77,loadedUserDataSet.getAge());
    }
}
