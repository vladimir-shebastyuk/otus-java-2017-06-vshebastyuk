package ru.otus.l131.cache;

/**
 * Интерфейс предоставляющий доступ к данным кэша
 */
public interface CacheMonitoring {

    /**
     * @return Кол-во попаданий в кэш при обращении к кэшу
     */
    int getHitCount();

    /**
     * @return Кол-во промахов при обращении к кэшу
     */
    int getMissCount();
}
