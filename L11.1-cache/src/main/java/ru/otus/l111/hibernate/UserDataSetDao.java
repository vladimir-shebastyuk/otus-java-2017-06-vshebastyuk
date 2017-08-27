package ru.otus.l111.hibernate;

import org.hibernate.Session;
import ru.otus.l111.datasets.UserDataSet;

/**
 * DAO для работы с {@link UserDataSet}
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
        return this.session.get(UserDataSet.class,id);
    }
}
