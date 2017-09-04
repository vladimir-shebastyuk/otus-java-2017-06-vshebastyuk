package ru.otus.l131.dbservice;

import ru.otus.l131.datasets.AddressDataSet;
import ru.otus.l131.datasets.PhoneDataSet;
import ru.otus.l131.datasets.UserDataSet;

import java.io.Closeable;

/**
 *
 */
public interface DbService extends Closeable{
    public void saveUser(UserDataSet dataSet) throws DbServiceException;
    public UserDataSet loadUser(long id) throws DbServiceException;
    public AddressDataSet loadAddress(long id) throws DbServiceException;
    public PhoneDataSet loadPhone(long id) throws DbServiceException;
}
