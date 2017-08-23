package ru.otus.l91.hibernate;

import org.hibernate.Session;
import ru.otus.l91.datasets.UserDataSet;

/**
 * DAO для работы с UserDataSet
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
