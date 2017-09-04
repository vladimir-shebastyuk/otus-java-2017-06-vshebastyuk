package ru.otus.l131.cache;

import ru.otus.l131.cache.engine.CacheEngine;
import ru.otus.l131.cache.engine.CacheEngineImpl;

import java.util.function.Supplier;

/**
 *
 */
public class CacheEngineFactory implements Supplier<CacheEngine> {

    private int maxElements;
    private int lifeTimeMs;
    private int idleTimeMs;
    private boolean isEternal;

    /**
     * s
     * @param maxElements
     * @param lifeTimeMs
     * @param idleTimeMs
     * @param isEternal
     */
    public CacheEngineFactory(int maxElements, int lifeTimeMs, int idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
    }

    @Override
    public CacheEngine get() {
        return new CacheEngineImpl(
                maxElements, lifeTimeMs, idleTimeMs, isEternal
        );
    }
}
