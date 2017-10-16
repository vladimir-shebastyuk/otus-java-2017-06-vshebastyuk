package ru.otus.backend.cache;


import ru.otus.common.services.cache.CacheService;
import ru.otus.common.services.cache.engine.CacheEngine;
import ru.otus.common.services.db.DbService;
import ru.otus.common.services.db.DbServiceException;
import ru.otus.common.services.db.datasets.AddressDataSet;
import ru.otus.common.services.db.datasets.PhoneDataSet;
import ru.otus.common.services.db.datasets.UserDataSet;
import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.context.MessageSystemContext;

import java.io.IOException;

/**
 * Декоратор для DbService, добавляющий кэширование
 */
public class DbServiceCacheDecorator implements DbService {
    private DbService decoratedDbService;

    private CacheEngine<Long,UserDataSet> usersCache;
    private CacheEngine<Long,AddressDataSet> addressesCache;
    private CacheEngine<Long,PhoneDataSet> phonesCache;

    private Address address;

    @SuppressWarnings("unchecked")
    public DbServiceCacheDecorator(DbService decoratedDbService, CacheService cacheService) {
        this.decoratedDbService = decoratedDbService;

        this.usersCache = cacheService.getCache(UserDataSet.class);
        this.addressesCache = cacheService.getCache(AddressDataSet.class);
        this.phonesCache = cacheService.getCache(PhoneDataSet.class);
    }

    public void registerInMessageSystem(MessageSystemContext context, Address address){
        this.address = address;
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void saveUser(UserDataSet userDataSet) throws DbServiceException {
        this.decoratedDbService.saveUser(userDataSet);

        putUserDataSetToCache(userDataSet);
    }

    protected void putUserDataSetToCache(UserDataSet userDataSet){
        if(userDataSet != null) {
            this.usersCache.put(userDataSet.getId(), userDataSet);

            if (userDataSet.getAddress() != null) {
                putAddressDataSetToCache(userDataSet.getAddress());
            }

            if (userDataSet.getPhones() != null) {
                for (PhoneDataSet phoneDataSet : userDataSet.getPhones()) {
                    putPhoneDataSetToCache(phoneDataSet);
                }
            }
        }
    }

    protected void putAddressDataSetToCache(AddressDataSet addressDataSet){
        if(addressDataSet != null){
            this.addressesCache.put(addressDataSet.getId(),addressDataSet);
        }
    }

    protected void putPhoneDataSetToCache(PhoneDataSet phoneDataSet){
        if(phoneDataSet != null) {
            this.phonesCache.put(phoneDataSet.getId(), phoneDataSet);
        }
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

    @Override
    public Address getAddress() {
        return address;
    }
}
