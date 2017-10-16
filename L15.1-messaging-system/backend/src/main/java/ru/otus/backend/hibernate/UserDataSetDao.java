package ru.otus.backend.hibernate;

import org.hibernate.Session;
import ru.otus.common.services.db.datasets.UserDataSet;
import ru.otus.backend.hibernate.datasets.UserDataSetImpl;

/**
 * DAO для работы с {@link UserDataSetImpl}
 */
public class UserDataSetDao {
    private Session session;

    public UserDataSetDao(Session session) {
        this.session = session;
    }

    public void save(UserDataSet userDataSet){
        this.session.saveOrUpdate(userDataSet);
    }

    public UserDataSet getById(long id){
        return this.session.get(UserDataSetImpl.class,id);
    }
}
