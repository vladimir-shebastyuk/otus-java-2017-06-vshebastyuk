package ru.otus.l21;

import java.util.function.*;

/**
 * Вычичляет разер объектов измерением фактички занимаеого размера в куче
 */
public class ObjectSizeMeter {
    private int objectsSampleSize = 10_000_000;
    private int iterationNumber = 5;
    private Runtime runtime;

    public ObjectSizeMeter(){
        this.runtime = Runtime.getRuntime();
    }

    public ObjectSizeMeter(int objectsSampleSize, int iterationNumber){
        this.runtime = Runtime.getRuntime();
        this.objectsSampleSize = objectsSampleSize;
        this.iterationNumber = iterationNumber;
    }

    /**
     * Размер выборки (количество создаваемых объектов) по-умолчанию
     * @return размер выборки (количество создаваемых объектов) по-умолчанию
     */
    public int getObjectsSampleSize() {
        return objectsSampleSize;
    }

    /**
     * Размер выборки (количество создаваемых объектов) по-умолчанию
     * @param objectsSampleSize размер выборки (количество создаваемых объектов) по-умолчанию
     */
    public void setObjectsSampleSize(int objectsSampleSize) {
        this.objectsSampleSize = objectsSampleSize;
    }

    /**
     * Количество прогонов по-умолчанию
     * @return количество прогонов по-умолчанию
     */
    public int getIterationNumber() {
        return iterationNumber;
    }

    /**
     * Количество прогонов по-умолчанию
     * @param iterationNumber количество прогонов по-умолчанию
     */
    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    /**
     * Вычисляет размер объекта, конструирумого с помощью переданной функции.
     * @param objectConstructor Функция возвращающая новый экземпляр объекта
     * @param iterationNumber Размер выборки (количество создаваемых объектов)
     * @param objectSampleSize Количество элементов создаваемых для вычисления размера (чем больше, тем точнее)
     * @return Размер в байтах
     * @throws InterruptedException
     */
    public long calculate(Supplier objectConstructor, int iterationNumber, int objectSampleSize) throws InterruptedException {
        long averageObjectSize;
        long objectsSizesSum = 0;

        Object[] objectArray;

        for(int i = 0; i < iterationNumber; i++){
            objectArray = null;

            this.runGC();

            objectArray = new Object[objectSampleSize];

            long initialOccupiedMemoryAmount = getUsedMemory();

            for (int j = 0; j < objectSampleSize; j++) {
                objectArray[j] = objectConstructor.get();
            }

            long occupiedMemoryAmountWithObjects = getUsedMemory();
            long objectSize =  Math.round( ((double)( occupiedMemoryAmountWithObjects - initialOccupiedMemoryAmount )) / (double)objectSampleSize );

            objectsSizesSum += objectSize;
        }

        averageObjectSize = objectsSizesSum / iterationNumber;

        return averageObjectSize;
    }

    /**
     * Вычисляет размер объекта, конструирумого с помощью переданной функции. Размер выборки и количество прогонов определяется
     * параметрами объекта.
     * @param objectConstructor Функция возвращающая новый экземпляр объекта
     * @return Размер в байтах
     */
    public long calculate(Supplier objectConstructor) throws InterruptedException{
        return this.calculate(objectConstructor,this.iterationNumber, this.objectsSampleSize);
    }

    private void runGC() throws InterruptedException {
        System.gc();
        Thread.sleep(50);
    }

    private long getUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
