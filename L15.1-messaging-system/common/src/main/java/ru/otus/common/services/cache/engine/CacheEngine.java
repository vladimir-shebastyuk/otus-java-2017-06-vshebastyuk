package ru.otus.common.services.cache.engine;

/**
 * Интерфейс кэш
 */
public interface CacheEngine<K, V> {

    void put(K key,V value);

    V get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
