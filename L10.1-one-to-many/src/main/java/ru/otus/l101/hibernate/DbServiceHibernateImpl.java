package ru.otus.l101.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.l101.datasets.AddressDataSet;
import ru.otus.l101.datasets.PhoneDataSet;
import ru.otus.l101.datasets.UserDataSet;
import ru.otus.l101.dbservice.DbService;
import ru.otus.l101.dbservice.DbServiceException;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 */
public class DbServiceHibernateImpl implements DbService{
    private final SessionFactory sessionFactory;


    public DbServiceHibernateImpl(Configuration configuration) {
        this.sessionFactory = createSessionFactory(configuration);
    }

    public DbServiceHibernateImpl(String url,String username, String password) {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url",url );
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        configuration.setProperty("hibernate.jdbc.time_zone", "UTC");

        this.sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void saveUser(UserDataSet dataSet) throws DbServiceException {
        runInSession(session -> {
            new UserDataSetDao(session).save(dataSet);
        });
    }

    @Override
    public UserDataSet loadUser(long id) throws DbServiceException {
        return runInSession(session -> {return new UserDataSetDao(session).getById(id);});
    }

    @Override
    public AddressDataSet loadAddress(long id) throws DbServiceException {
        return runInSession(session -> {return new AddressDataSetDao(session).getById(id);});
    }

    @Override
    public PhoneDataSet loadPhone(long id) throws DbServiceException {
        return runInSession(session -> {return new PhoneDataSetDao(session).getById(id);});
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void runInSession(Consumer<Session> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            function.accept(session);
            transaction.commit();
        }
    }

    @Override
    public void close() throws IOException {
        sessionFactory.close();
    }
}
