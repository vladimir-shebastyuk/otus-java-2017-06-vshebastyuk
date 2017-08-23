package ru.otus.l91.jdbc;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l91.DbServiceTestBase;
import ru.otus.l91.datasets.DataSet;
import ru.otus.l91.datasets.UserDataSet;
import ru.otus.l91.dbservice.DbService;
import ru.otus.l91.hibernate.DbServiceHibernateImpl;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public class DbServiceJdbcImplTest  extends DbServiceTestBase{
    @Before
    public void startDb() throws Exception {
        super.startDb();
        this.dbService = new DbServiceJdbcImpl(JDBC_URL,"","");
    }
}
