package ru.otus.backend.cache;

import org.junit.Before;
import org.junit.Test;
import ru.otus.backend.hibernate.datasets.AddressDataSetImpl;
import ru.otus.backend.hibernate.datasets.PhoneDataSetImpl;
import ru.otus.backend.hibernate.datasets.UserDataSetImpl;
import ru.otus.common.services.cache.CacheService;
import ru.otus.common.services.db.datasets.*;
import ru.otus.backend.hibernate.DbServiceHibernateBaseTest;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 */
public class DbServiceCacheDecoratorTest extends DbServiceHibernateBaseTest {
    private DbServiceCacheDecorator cachedDbService;
    private CacheService cacheService;
    private final static long EXPIRE_TIME = 100;

    private int hit = 0;
    private int miss = 0;

    @Before
    public void setUp() throws Exception {
        this.dbService = spy(this.dbService);
        this.cacheService = new CacheServiceImpl(10,EXPIRE_TIME,0,false);

        cachedDbService = new DbServiceCacheDecorator(
            dbService,
            cacheService
        );
    }

    protected void shouldMiss(){
        assertEquals(hit,this.cacheService.getTotalHitCount());
        assertEquals(++miss,this.cacheService.getTotalMissCount());
    }

    protected void shouldHit(){
        assertEquals(++hit,this.cacheService.getTotalHitCount());
        assertEquals(miss,this.cacheService.getTotalMissCount());
    }

    /**
     * Проверяем сохранение в кэш
     * @throws Exception
     */
    @Test
    public void saveLoadHitMiss() throws Exception {
        cachedDbService.saveUser(
                new UserDataSetImpl("Name", 18,
                        new AddressDataSetImpl(
                                "Baker st."
                        ),
                        new HashSet<>(Arrays.asList(
                            new PhoneDataSetImpl("111"),
                            new PhoneDataSetImpl("222")
                        ))
                )
        );

        UserDataSet userDataSet = cachedDbService.loadUser(1);

        assertEquals("Name", userDataSet.getName());
        shouldHit();
        verify(this.dbService, times(0)).loadUser(1L);

        AddressDataSet loadedAddressDataSet = cachedDbService.loadAddress(1L);
        assertEquals(userDataSet.getAddress(),loadedAddressDataSet);
        assertEquals("Baker st.", loadedAddressDataSet.getStreet());
        shouldHit();
        verify(this.dbService, times(0)).loadAddress(1L);

        PhoneDataSet loadedPhoneDataSet = cachedDbService.loadPhone(1L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("111", loadedPhoneDataSet.getNumber());
        shouldHit();
        verify(this.dbService, times(0)).loadPhone(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(2L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("222", loadedPhoneDataSet.getNumber());
        shouldHit();
        verify(this.dbService, times(0)).loadPhone(2L);


        //проверяем через EXPIRE_TIME * 2ms, что данные из кэша удалились и мы прочитаем значения из базы напрямую
        Thread.sleep(EXPIRE_TIME * 2);

        userDataSet = cachedDbService.loadUser(1);
        assertEquals("Name", userDataSet.getName());
        shouldMiss();
        verify(this.dbService, times(1)).loadUser(1L);

        Thread.sleep(EXPIRE_TIME * 2);

        loadedAddressDataSet = cachedDbService.loadAddress(1L);
        assertEquals(userDataSet.getAddress(),loadedAddressDataSet);
        assertEquals("Baker st.", loadedAddressDataSet.getStreet());
        shouldMiss();
        verify(this.dbService, times(1)).loadAddress(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(1L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("111", loadedPhoneDataSet.getNumber());
        shouldMiss();
        verify(this.dbService, times(1)).loadPhone(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(2L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("222", loadedPhoneDataSet.getNumber());
        shouldMiss();
        verify(this.dbService, times(1)).loadPhone(2L);
    }

    /**
     * Проверяем чтение элементов, которые не были сохранены с помощью кэшированного DbService
     * @throws Exception
     */
    @Test
    public void loadNotSaved() throws Exception {
        dbService.saveUser(
                new UserDataSetImpl("Name", 18,
                        new AddressDataSetImpl(
                                "Baker st."
                        ),
                        new HashSet<>(
                                Arrays.asList(
                                    new PhoneDataSetImpl("111"),
                                    new PhoneDataSetImpl("222")
                                )
                        )
                )
        );

        //читаем первый раз данные, которых еще нет в кэше
        UserDataSet userDataSet = cachedDbService.loadUser(1);
        assertEquals("Name", userDataSet.getName());
        shouldMiss();
        verify(this.dbService, times(1)).loadUser(1L);

        //читаем втрой раз, те же данные и убеждаемся, что они попали в кэш
        userDataSet = cachedDbService.loadUser(1);

        assertEquals("Name", userDataSet.getName());
        shouldHit();
        verify(this.dbService, times(1)).loadUser(1L);

        AddressDataSet loadedAddressDataSet = cachedDbService.loadAddress(1L);
        assertEquals(userDataSet.getAddress(),loadedAddressDataSet);
        assertEquals("Baker st.", loadedAddressDataSet.getStreet());
        shouldHit();
        verify(this.dbService, times(0)).loadAddress(1L);

        PhoneDataSet loadedPhoneDataSet = cachedDbService.loadPhone(1L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("111", loadedPhoneDataSet.getNumber());
        shouldHit();
        verify(this.dbService, times(0)).loadPhone(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(2L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("222", loadedPhoneDataSet.getNumber());
        shouldHit();
        verify(this.dbService, times(0)).loadPhone(2L);

        Thread.sleep(EXPIRE_TIME * 2);

        loadedAddressDataSet = cachedDbService.loadAddress(1L);
        assertEquals(userDataSet.getAddress(),loadedAddressDataSet);
        assertEquals("Baker st.", loadedAddressDataSet.getStreet());
        shouldMiss();
        verify(this.dbService, times(1)).loadAddress(1L);

        loadedAddressDataSet = cachedDbService.loadAddress(1L);
        assertEquals(userDataSet.getAddress(),loadedAddressDataSet);
        assertEquals("Baker st.", loadedAddressDataSet.getStreet());
        shouldHit();
        verify(this.dbService, times(1)).loadAddress(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(1L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("111", loadedPhoneDataSet.getNumber());
        shouldMiss();
        verify(this.dbService, times(1)).loadPhone(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(2L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("222", loadedPhoneDataSet.getNumber());
        shouldMiss();
        verify(this.dbService, times(1)).loadPhone(2L);

        loadedPhoneDataSet = cachedDbService.loadPhone(1L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("111", loadedPhoneDataSet.getNumber());
        shouldHit();
        verify(this.dbService, times(1)).loadPhone(1L);

        loadedPhoneDataSet = cachedDbService.loadPhone(2L);
        assertTrue(userDataSet.getPhones().contains(loadedPhoneDataSet));
        assertEquals("222", loadedPhoneDataSet.getNumber());
        shouldHit();
        verify(this.dbService, times(1)).loadPhone(2L);
    }
}