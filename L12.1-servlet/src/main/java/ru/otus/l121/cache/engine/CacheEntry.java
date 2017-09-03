package ru.otus.l121.cache.engine;

import java.lang.ref.SoftReference;

/**
 * Запись внутри кэша, содержащая хранимое значение, ключ и информацию по времени создания и доступа
 */
@SuppressWarnings("WeakerAccess")
public class CacheEntry<K, V> {
    private final K key;
    private final SoftReference<V> value;
    private final long creationTime;
    private long lastAccessTime;

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = new SoftReference<V>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value.get();
    }

    public boolean isDeletedByGC(){
        return value.get() == null;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
