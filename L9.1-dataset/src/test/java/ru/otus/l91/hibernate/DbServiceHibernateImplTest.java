package ru.otus.l91.hibernate;

import org.hibernate.cfg.Configuration;
import org.junit.*;
import ru.otus.l91.DbServiceTestBase;
import ru.otus.l91.datasets.DataSet;
import ru.otus.l91.datasets.UserDataSet;
import ru.otus.l91.dbservice.DbService;
import ru.otus.l91.jdbc.simpleorm.SimpleOrm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class DbServiceHibernateImplTest extends DbServiceTestBase{
    @Before
    public void startDb() throws Exception {
        super.startDb();

        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url",JDBC_URL );
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        this.dbService = new DbServiceHibernateImpl(configuration);
    }
}
