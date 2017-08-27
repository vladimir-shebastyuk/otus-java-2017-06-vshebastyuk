package ru.otus.l111.cache.engine;

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
