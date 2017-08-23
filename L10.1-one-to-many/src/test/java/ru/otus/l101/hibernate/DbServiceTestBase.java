package ru.otus.l101.hibernate;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l101.datasets.AddressDataSet;
import ru.otus.l101.datasets.DataSet;
import ru.otus.l101.datasets.PhoneDataSet;
import ru.otus.l101.datasets.UserDataSet;
import ru.otus.l101.dbservice.DbService;
import ru.otus.l101.hibernate.DbServiceHibernateImpl;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public abstract class DbServiceTestBase {
    protected static final String JDBC_URL = "jdbc:h2:mem:test";
    protected static final String JDBC_INIT_URL = "jdbc:h2:mem:test;INIT=runscript from 'classpath:/scripts/create-tables.sql'";

    protected Connection connection;
    protected DbService dbService;

    @Before
    public void startDb() throws Exception {
        connection = DriverManager.getConnection(JDBC_INIT_URL);

        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

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
