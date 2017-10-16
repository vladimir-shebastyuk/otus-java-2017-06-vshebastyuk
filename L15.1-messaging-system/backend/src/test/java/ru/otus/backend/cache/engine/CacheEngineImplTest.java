package ru.otus.backend.cache.engine;

import org.junit.Test;
import ru.otus.common.services.cache.engine.CacheEngine;

import static org.junit.Assert.*;

/**
 *
 */
public class CacheEngineImplTest {
    @Test
    public void putAndGet() throws Exception {
        CacheEngine<Long,String> cacheEngine = new CacheEngineImpl<>(10,0,0,true);

        cacheEngine.put(1L, "First");

        assertEquals("First",cacheEngine.get(1L));
        assertNull(cacheEngine.get(2L));

        cacheEngine.put(2L, "Second");

        assertEquals("First",cacheEngine.get(1L));
        assertEquals("Second",cacheEngine.get(2L));
    }

    @Test
    public void getHitCountAndGetMissCount() throws Exception {
        CacheEngine<Long,String> cacheEngine = new CacheEngineImpl<>(10,0,0,true);

        cacheEngine.put(1L, "First");

        assertEquals("First",cacheEngine.get(1L));
        assertNull(cacheEngine.get(2L));

        cacheEngine.put(2L, "Second");

        assertEquals("First",cacheEngine.get(1L));
        assertEquals("Second",cacheEngine.get(2L));

        assertEquals(3, cacheEngine.getHitCount());
        assertEquals(1, cacheEngine.getMissCount());
    }

    @Test
    public void softReferencesTest() throws Exception {
        long maxMemory = Runtime.getRuntime().maxMemory();

        //на всякий случай чистим память
        Runtime.getRuntime().gc();

        int bigObjectsCountToFillMemory = (int)maxMemory / BigObject.SIZE + 1;

        CacheEngine<Long,BigObject> cacheEngine = new CacheEngineImpl<>(bigObjectsCountToFillMemory + 1,0,0,true);

        cacheEngine.put(1L, new BigObject());

        assertNotNull(cacheEngine.get(1L));

        final long idShift = 1000L;

        for(int i = 0; i < bigObjectsCountToFillMemory;i++){
            cacheEngine.put(idShift + i,new BigObject());
        }

        //на данном моменте, первый добавленный элемент должен быть удален, т.к должен быть собран сборщиком мусора

        assertNull(cacheEngine.get(1L));

        long lastElementsCount = 0;

        for(int i = 0; i < bigObjectsCountToFillMemory;i++){
            if(cacheEngine.get(idShift + i) != null){
                lastElementsCount++;
            }
        }

        //после всех манипуляций с добавлением элементов, хоть какие-нибудь должны остаться
        assertTrue( lastElementsCount > 0);

        //еще раз чистим память на всякий случай
        Runtime.getRuntime().gc();
    }

    private class BigObject{
        private final static int SIZE = 1_000_000;
        private byte[] payload = new byte[SIZE];
    }
}