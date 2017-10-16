package ru.otus.backend.cache;


import ru.otus.backend.cache.engine.CacheEngineImpl;
import ru.otus.common.services.cache.CacheService;
import ru.otus.common.services.cache.engine.CacheEngine;
import ru.otus.common.messagesystem.Address;
import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.context.MessageSystemContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация сервиса управляющая кэшами
 */
public class CacheServiceImpl implements CacheService {
    protected Map<String,CacheEngine> caches = new HashMap<>();

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private Address address;

    public CacheServiceImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
    }

    public void registerInMessageSystem(MessageSystemContext context, Address address){
        this.address = address;
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public <T> CacheEngine<Long, T> getCache(Class<T> clazz) {
        String key = classToCacheName(clazz);
        if(!caches.containsKey(key)){
            caches.put(key, new CacheEngineImpl(maxElements,lifeTimeMs,idleTimeMs,isEternal));
        }

        return caches.get(key);
    }

    @Override
    public int getTotalHitCount() {
        return caches.values().stream().mapToInt(CacheEngine::getHitCount).sum();
    }

    @Override
    public int getTotalMissCount() {
        return caches.values().stream().mapToInt(CacheEngine::getMissCount).sum();
    }

    protected String classToCacheName(Class clazz){
        return clazz.getCanonicalName();
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
