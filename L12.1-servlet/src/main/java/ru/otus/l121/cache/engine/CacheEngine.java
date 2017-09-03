package ru.otus.l121.cache.engine;

import ru.otus.l121.cache.CacheMonitoring;

/**
 * Интерфейс кэш
 */
public interface CacheEngine<K, V> extends CacheMonitoring {

    void put(K key,V value);

    V get(K key);

    void dispose();
}
