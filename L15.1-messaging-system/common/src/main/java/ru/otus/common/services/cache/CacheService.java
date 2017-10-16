package ru.otus.common.services.cache;


import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.services.cache.engine.CacheEngine;

/**
 * Сервис управления кэшами
 */
public interface CacheService extends Addressee {
    /**
     * Вовращает существующий или создает новый кэш для элементов указанного типа
     * @param clazz
     * @param <T>
     * @return
     */
    <T> CacheEngine<Long,T> getCache(Class<T> clazz);

    int getTotalHitCount();
    int getTotalMissCount();

}
