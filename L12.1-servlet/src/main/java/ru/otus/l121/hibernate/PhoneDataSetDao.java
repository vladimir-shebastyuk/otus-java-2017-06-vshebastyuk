package ru.otus.l121.hibernate;

import org.hibernate.Session;
import ru.otus.l121.datasets.PhoneDataSet;

/**
 * DAO для работы с {@link PhoneDataSet}
 */
public class PhoneDataSetDao {
    private Session session;

    public PhoneDataSetDao(Session session) {
        this.session = session;
    }

    public PhoneDataSet getById(long id){
        return this.session.get(PhoneDataSet.class,id);
    }
}