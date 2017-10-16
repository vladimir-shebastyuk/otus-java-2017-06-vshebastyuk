package ru.otus.backend.hibernate;

import org.hibernate.Session;
import ru.otus.common.services.db.datasets.AddressDataSet;
import ru.otus.backend.hibernate.datasets.AddressDataSetImpl;

/**
 * DAO для работы с {@link AddressDataSetImpl}
 */
public class AddressDataSetDao {
    private Session session;

    public AddressDataSetDao(Session session) {
        this.session = session;
    }

    public AddressDataSet getById(long id){
        return this.session.get(AddressDataSetImpl.class,id);
    }
}
