package ru.otus.l91.jdbc.simpleorm;

import org.junit.*;
import ru.otus.l91.datasets.DataSet;
import ru.otus.l91.datasets.UserDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class SimpleOrmTest {
    private static final String JDBC_URL = "jdbc:h2:mem:test;INIT=runscript from 'classpath:/scripts/create-users-table.sql'";

    private static Connection connection;

    @BeforeClass
    public static void beforeClass() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
    }

    @Before
    public void startDb() throws Exception {
        connection = DriverManager.getConnection(JDBC_URL);
    }

    @After
    public void stopDb() throws SQLException {
        connection.close();
    }

    @Test
    public void autoGenerateIdTest() throws Exception {
        SimpleOrm simpleOrm = new SimpleOrm(connection);

        UserDataSet userDataSet = new UserDataSet("test",15);
        simpleOrm.save(userDataSet);

        Assert.assertEquals(1L,userDataSet.getId());

        userDataSet = new UserDataSet("test2",18);
        simpleOrm.save(userDataSet);

        Assert.assertEquals(2L,userDataSet.getId());
    }

    @Test
    public void saveLoad() throws Exception {
        SimpleOrm simpleOrm = new SimpleOrm(connection);

        UserDataSet userDataSet = new UserDataSet("test",15);
        simpleOrm.save(userDataSet);

        long id = userDataSet.getId();

        DataSet loadedUserDataSet = simpleOrm.load(id, UserDataSet.class);

        Assert.assertEquals(userDataSet,loadedUserDataSet);
    }

    @Test
    public void updateExiting() throws Exception {
        SimpleOrm simpleOrm = new SimpleOrm(connection);

        //1. создаем пользователя
        UserDataSet userDataSet = new UserDataSet("test",15);
        simpleOrm.save(userDataSet);

        long id = userDataSet.getId();

        //2. Изменяем его и снова сохраняем
        userDataSet.setAge(77);
        userDataSet.setName("New Name");

        simpleOrm.save(userDataSet);

        //3. проверяем, что id не поменялся
        Assert.assertEquals(id,userDataSet.getId());

        //4. Вычитываем из базы по id
        id = userDataSet.getId();

        UserDataSet loadedUserDataSet = (UserDataSet)simpleOrm.load(id, UserDataSet.class);

        Assert.assertEquals("New Name",loadedUserDataSet.getName());
        Assert.assertEquals(77,loadedUserDataSet.getAge());
    }


}
