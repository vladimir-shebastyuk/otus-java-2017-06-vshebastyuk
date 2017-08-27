package ru.otus.l111.cache;

import ru.otus.l111.cache.engine.CacheEngine;
import ru.otus.l111.datasets.AddressDataSet;
import ru.otus.l111.datasets.PhoneDataSet;
import ru.otus.l111.datasets.UserDataSet;
import ru.otus.l111.dbservice.DbService;
import ru.otus.l111.dbservice.DbServiceException;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Декоратор для DbService, добавляющий кэширование
 */
public class DbServiceCacheDecorator implements DbService {
    private DbService decoratedDbService;

    private CacheEngine<Long,UserDataSet> usersCache;
    private CacheEngine<Long,AddressDataSet> addressesCache;
    private CacheEngine<Long,PhoneDataSet> phonesCache;

    @SuppressWarnings("unchecked")
    public DbServiceCacheDecorator(DbService decoratedDbService, Supplier<CacheEngine> cacheEngineConstructor) {
        this.decoratedDbService = decoratedDbService;

        this.usersCache = cacheEngineConstructor.get();
        this.addressesCache = cacheEngineConstructor.get();
        this.phonesCache = cacheEngineConstructor.get();
    }

    @Override
    public void saveUser(UserDataSet userDataSet) throws DbServiceException {
        this.decoratedDbService.saveUser(userDataSet);

        putUserDataSetToCache(userDataSet);
    }

    protected void putUserDataSetToCache(UserDataSet userDataSet){
        this.usersCache.put(userDataSet.getId(),userDataSet);

        if(userDataSet.getAddress() != null){
            putAddressDataSetToCache(userDataSet.getAddress());
        }

        if(userDataSet.getPhones() != null) {
            for (PhoneDataSet phoneDataSet : userDataSet.getPhones()) {
                putPhoneDataSetToCache(phoneDataSet);
            }
        }
    }

    protected void putAddressDataSetToCache(AddressDataSet addressDataSet){
        this.addressesCache.put(addressDataSet.getId(),addressDataSet);
    }

    protected void putPhoneDataSetToCache(PhoneDataSet phoneDataSet){
        this.phonesCache.put(phoneDataSet.getId(), phoneDataSet);
    }

    @Override
    public UserDataSet loadUser(long id) throws DbServiceException {
        UserDataSet userDataSet = this.usersCache.get(id);

        if(userDataSet == null){
            userDataSet = this.decoratedDbService.loadUser(id);
            putUserDataSetToCache(userDataSet);
        }

        return userDataSet;
    }

    @Override
    public AddressDataSet loadAddress(long id) throws DbServiceException {
        AddressDataSet addressDataSet = this.addressesCache.get(id);

        if(addressDataSet == null){
            addressDataSet = this.decoratedDbService.loadAddress(id);
            putAddressDataSetToCache(addressDataSet);
        }

        return addressDataSet;
    }

    @Override
    public PhoneDataSet loadPhone(long id) throws DbServiceException {
        PhoneDataSet phoneDataSet = this.phonesCache.get(id);

        if(phoneDataSet == null){
            phoneDataSet = this.decoratedDbService.loadPhone(id);
            putPhoneDataSetToCache(phoneDataSet);
        }

        return phoneDataSet;
    }

    @Override
    public void close() throws IOException {
        this.decoratedDbService.close();
        this.usersCache.dispose();
        this.addressesCache.dispose();
        this.phonesCache.dispose();
    }

    /**
     * Кол-во попаданий в кэш при обращении к методам
     * @return
     */
    public int getHit(){
        return this.usersCache.getHitCount() + this.addressesCache.getHitCount() + this.phonesCache.getHitCount();
    }

    /**
     * Кол-во промахов при обращении к методам
     * @return
     */
    public int getMiss(){
        return this.usersCache.getMissCount() + this.addressesCache.getMissCount() + this.phonesCache.getMissCount();
    }

}
