package ru.otus.l131;

/**
 * Исполнитель имитатора бурной деятельсности
 */
public class HardWorkerRunner {
    private final HardWorker hardWorker;

    public HardWorkerRunner(HardWorker hardWorker) {
        this.hardWorker = hardWorker;
    }

    public void start(){
        (new Thread(this.hardWorker)).start();
    }

    public void stop(){
        this.hardWorker.setStopped(true);
    }
}
