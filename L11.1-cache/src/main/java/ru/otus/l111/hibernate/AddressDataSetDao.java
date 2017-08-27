package ru.otus.l111.hibernate;

import org.hibernate.Session;
import ru.otus.l111.datasets.AddressDataSet;

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
