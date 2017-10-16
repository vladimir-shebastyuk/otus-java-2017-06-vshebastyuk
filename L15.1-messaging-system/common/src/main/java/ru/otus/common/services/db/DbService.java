package ru.otus.common.services.db;

import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.services.db.datasets.AddressDataSet;
import ru.otus.common.services.db.datasets.PhoneDataSet;
import ru.otus.common.services.db.datasets.UserDataSet;

import java.io.Closeable;

/**
 *
 */
public interface DbService extends Closeable, Addressee{
    public void saveUser(UserDataSet dataSet) throws DbServiceException;
    public UserDataSet loadUser(long id) throws DbServiceException;
    public AddressDataSet loadAddress(long id) throws DbServiceException;
    public PhoneDataSet loadPhone(long id) throws DbServiceException;
}
