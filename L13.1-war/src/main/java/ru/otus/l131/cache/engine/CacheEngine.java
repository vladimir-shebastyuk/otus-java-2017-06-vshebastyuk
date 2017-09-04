package ru.otus.l131.cache.engine;

import ru.otus.l131.cache.CacheMonitoring;

/**
 * Интерфейс кэш
 */
public interface CacheEngine<K, V> extends CacheMonitoring {

    void put(K key,V value);

    V get(K key);

    void dispose();
}
