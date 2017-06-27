package ru.otus.l41;

/**
 * "Подтекающий" класс
 */
public class LeakingClass implements Runnable {
    private int startSize = 1_000_000;
    private int finalSize = 10_000_000;
    private int sizeIncrement = 500_00;

    private double recreatedObjectsRatio = .5;
    private long sleepTime = 2000L;

    private boolean doLogging = false;

    public LeakingClass setStartSize(int startSize) {
        this.startSize = startSize;
        return this;
    }

    public LeakingClass setFinalSize(int finalSize) {
        this.finalSize = finalSize;
        return this;
    }

    public LeakingClass setSizeIncrement(int sizeIncrement) {
        this.sizeIncrement = sizeIncrement;
        return this;
    }

    public LeakingClass setRecreatedObjectsRatio(double recreatedObjectsRatio) {
        this.recreatedObjectsRatio = recreatedObjectsRatio;
        return this;
    }

    public LeakingClass setDoLogging(boolean doLogging) {
        this.doLogging = doLogging;
        return this;
    }

    public LeakingClass setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public void run() {
        String[] array = new String[this.finalSize];

        int currentSize = this.startSize;
        int lastSize = 0;

        while(true){
            if(doLogging) System.out.println("Creating string objects...");
            for(int i = lastSize ; i < currentSize; i ++){
                array[i] = new String(new char[10]);
            }

            //выбираем произвольное кол-во элементов, которые мы создадим по новой
            int recreatedSize = (int)(Math.random() * currentSize * this.recreatedObjectsRatio);

            for(int i = currentSize - recreatedSize ; i < currentSize; i ++){
                array[i] = new String(new char[10]);
            }

            if(doLogging) System.out.println("Go to sleep");

            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //постепенно увеличиваем количество создаваемых элементов
            if(( currentSize + this.sizeIncrement ) <= this.finalSize)
                currentSize += this.sizeIncrement;
        }
    }
}
