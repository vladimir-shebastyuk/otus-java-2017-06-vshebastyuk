package ru.otus.backend.hibernate;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import ru.otus.backend.hibernate.datasets.AddressDataSetImpl;
import ru.otus.backend.hibernate.datasets.PhoneDataSetImpl;

import ru.otus.backend.hibernate.datasets.UserDataSetImpl;
import ru.otus.common.services.db.DbService;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public abstract class DbServiceHibernateBaseTest {
    protected static final String JDBC_URL = "jdbc:h2:mem:test";
    protected static final String JDBC_INIT_URL = "jdbc:h2:mem:test;INIT=runscript from 'classpath:/scripts/create-tables.sql'";

    protected Connection connection;
    protected DbService dbService;

    @Before
    public void startDb() throws Exception {
        connection = DriverManager.getConnection(JDBC_INIT_URL);

        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSetImpl.class);
        configuration.addAnnotatedClass(AddressDataSetImpl.class);
        configuration.addAnnotatedClass(PhoneDataSetImpl.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url",JDBC_URL );
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        this.dbService = new DbServiceHibernateImpl(configuration);
    }

    @After
    public void stopDb() throws Exception {
        this.connection.close();
        this.dbService.close();
    }
}
