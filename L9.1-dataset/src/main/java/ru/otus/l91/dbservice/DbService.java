package ru.otus.l91.dbservice;

import ru.otus.l91.datasets.DataSet;
import ru.otus.l91.datasets.UserDataSet;

import java.io.Closeable;

/**
 *
 */
public interface DbService extends Closeable{
    public void saveUser(UserDataSet dataSet) throws DbServiceException;
    public UserDataSet loadUser(long id) throws DbServiceException;
}
