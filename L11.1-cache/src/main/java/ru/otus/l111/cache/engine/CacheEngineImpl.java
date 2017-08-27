package ru.otus.l111.cache.engine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

/**
 * Кэш позволяющие определять следующие переметры хранения:
 *  - время жизни записи
 *  - время без обращений
 *  - "вечное" хранение
 *  - максимальное число хранимых элементов
 *  Записи в кэше хранятся в виде SoftReference ссылок, поэтому в случае нехватки памяти и осутствия ссылок,
 *  они могут быть удалены при сборке мусора
 */
public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 1;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, CacheEntry<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @SuppressWarnings("unchecked")
    public void put(K key, V value) {
        CacheEntry element = new CacheEntry<>(key,value);

        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, element);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs);
            }
        }
    }

    public V get(K key) {
        CacheEntry<K, V> element = elements.get(key);

        if (element != null) {
            if(!element.isDeletedByGC()){
                hit++;
                element.setAccessed();

                return element.getValue();
            }else{
                //если значение по SoftReference было удалено
                //то заодно удаляем и элемент
                elements.remove(key);
            }
        }

        miss++;
        return null;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheEntry<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheEntry<K, V> checkedElement = elements.get(key);
                if (checkedElement == null || !checkedElement.isDeletedByGC() ||
                        isT1BeforeT2(timeFunction.apply(checkedElement),getCurrentTimeMillis() )) {
                    elements.remove(key);
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    private long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }
}
