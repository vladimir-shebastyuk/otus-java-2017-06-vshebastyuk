package ru.otus.l91.jdbc;

import ru.otus.l91.datasets.UserDataSet;
import ru.otus.l91.dbservice.DbService;
import ru.otus.l91.dbservice.DbServiceException;
import ru.otus.l91.jdbc.simpleorm.SimpleOrm;
import ru.otus.l91.jdbc.simpleorm.SimpleOrmException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB сервис на базе JDBC
 */
public class DbServiceJdbcImpl implements DbService{
    private String url;
    private String username;
    private String password;

    public DbServiceJdbcImpl(String url,String username, String password) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            this.url = url;
            this.username = username;
            this.password = password;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    this.url,
                    this.username,
                    this.password
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(UserDataSet dataSet) throws DbServiceException {
        try(Connection connection = getConnection()) {
            SimpleOrm simpleOrm = new SimpleOrm(connection);
            simpleOrm.save(dataSet);
        }catch (SQLException | SimpleOrmException ex){
            throw new DbServiceException(ex);
        }
    }

    @Override
    public UserDataSet loadUser(long id) throws DbServiceException {
        try(Connection connection = getConnection()) {
            SimpleOrm simpleOrm = new SimpleOrm(connection);
            return (UserDataSet)simpleOrm.load(id, UserDataSet.class);
        }catch (SQLException | SimpleOrmException ex){
            throw new DbServiceException(ex);
        }
    }

    @Override
    public void close() throws IOException {
    }
}
