package ru.otus.l101.hibernate;

import org.hibernate.Session;
import ru.otus.l101.datasets.AddressDataSet;
import ru.otus.l101.datasets.UserDataSet;

/**
 * DAO для работы с {@link AddressDataSet}
 */
public class AddressDataSetDao {
    private Session session;

    public AddressDataSetDao(Session session) {
        this.session = session;
    }

    public AddressDataSet getById(long id){
        return this.session.get(AddressDataSet.class,id);
    }
}
