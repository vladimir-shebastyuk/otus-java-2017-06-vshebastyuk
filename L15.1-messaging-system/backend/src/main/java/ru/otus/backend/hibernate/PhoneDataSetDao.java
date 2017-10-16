package ru.otus.backend.hibernate;

import org.hibernate.Session;
import ru.otus.common.services.db.datasets.PhoneDataSet;
import ru.otus.backend.hibernate.datasets.PhoneDataSetImpl;

/**
 * DAO для работы с {@link PhoneDataSetImpl}
 */
public class PhoneDataSetDao {
    private Session session;

    public PhoneDataSetDao(Session session) {
        this.session = session;
    }

    public PhoneDataSet getById(long id){
        return this.session.get(PhoneDataSetImpl.class,id);
    }
}
