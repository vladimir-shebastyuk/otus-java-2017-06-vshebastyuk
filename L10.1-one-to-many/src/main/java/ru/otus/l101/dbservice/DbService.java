package ru.otus.l101.dbservice;

import ru.otus.l101.datasets.AddressDataSet;
import ru.otus.l101.datasets.PhoneDataSet;
import ru.otus.l101.datasets.UserDataSet;

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
