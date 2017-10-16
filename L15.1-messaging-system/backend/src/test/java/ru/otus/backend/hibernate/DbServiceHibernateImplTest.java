package ru.otus.backend.hibernate;

import org.junit.*;
import ru.otus.backend.hibernate.datasets.AddressDataSetImpl;
import ru.otus.backend.hibernate.datasets.PhoneDataSetImpl;
import ru.otus.backend.hibernate.datasets.UserDataSetImpl;
import ru.otus.common.services.db.datasets.*;

/**
 *
 */
public class DbServiceHibernateImplTest extends DbServiceHibernateBaseTest {

    @Test
    public void autoGenerateIdTest() throws Exception {
        UserDataSetImpl userDataSet = new UserDataSetImpl("test", 15);
        this.dbService.saveUser(userDataSet);

        Assert.assertEquals(1L, userDataSet.getId());

        userDataSet = new UserDataSetImpl("test2", 18);
        this.dbService.saveUser(userDataSet);

        Assert.assertEquals(2L, userDataSet.getId());
    }

    @Test
    public void saveLoad() throws Exception {
        UserDataSetImpl userDataSet = new UserDataSetImpl("test", 15);
        this.dbService.saveUser(userDataSet);

        long id = userDataSet.getId();

        DataSet loadedUserDataSet = this.dbService.loadUser(id);

        Assert.assertEquals(userDataSet, loadedUserDataSet);
    }

    @Test
    public void updateExiting() throws Exception {
        //1. создаем пользователя
        UserDataSetImpl userDataSet = new UserDataSetImpl("test", 15);
        this.dbService.saveUser(userDataSet);

        long id = userDataSet.getId();

        //2. Изменяем его и снова сохраняем
        userDataSet.setAge(77);
        userDataSet.setName("New Name");

        this.dbService.saveUser(userDataSet);

        //3. проверяем, что id не поменялся
        Assert.assertEquals(id, userDataSet.getId());

        //4. Вычитываем из базы по id
        id = userDataSet.getId();

        UserDataSet loadedUserDataSet = this.dbService.loadUser(id);

        Assert.assertEquals("New Name", loadedUserDataSet.getName());
        Assert.assertEquals(77, loadedUserDataSet.getAge());
    }

    @Test
    public void saveWithAddress() throws Exception {
        dbService.saveUser(
                new UserDataSetImpl("Name", 18,
                        new AddressDataSetImpl(
                                "Baker st."
                        )
                )
        );

        UserDataSet userDataSet = dbService.loadUser(1);

        Assert.assertEquals("Name", userDataSet.getName());

        AddressDataSet loadedAddressDataSet = dbService.loadAddress(1L);

        Assert.assertEquals(userDataSet.getAddress(),loadedAddressDataSet);
        Assert.assertEquals("Baker st.", loadedAddressDataSet.getStreet());
    }

    @Test
    public void saveWithPhones() throws Exception {
        UserDataSet userDataSet = new UserDataSetImpl("Name", 18);

        userDataSet.addPhone(new PhoneDataSetImpl("+7-777-111-222-33"));

        dbService.saveUser(userDataSet);

        userDataSet.addPhone(new PhoneDataSetImpl("+7-777-444-555-66"));

        dbService.saveUser(userDataSet);

        userDataSet = dbService.loadUser(1);
        Assert.assertEquals("Name", userDataSet.getName());

        PhoneDataSet loadedPhoneDataSet = dbService.loadPhone(1L);
        Assert.assertEquals("+7-777-111-222-33", loadedPhoneDataSet.getNumber());

        loadedPhoneDataSet = dbService.loadPhone(2L);
        Assert.assertEquals("+7-777-444-555-66", loadedPhoneDataSet.getNumber());
    }
}
