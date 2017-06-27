package ru.otus.l41;

import javax.management.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Варианты запуска со следущими параметрами:
 *      -Xms512m  -Xmx512m -XX:+UseSerialGC
 *      -Xms512m  -Xmx512m -XX:+UseParallelGC -XX:+UseParallelOldGC
 *      -Xms512m  -Xmx512m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
 *      -Xms512m  -Xmx512m -XX:+UseG1GC
 *
 */
public class Main {
    public static void main(String... args) throws InterruptedException, MalformedObjectNameException {
        final GcStatisticsCollector gcStatisticsCollector = new GcStatisticsCollector(
             (String gcName, String gcType, long id, String cause, long duration) ->{
                 //без логгирования события сборки
            }
        );

        //Периодически собираем и рабопортуем статистику по сборке мусора
        long interval = 60L;

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(new GcStatisticsLogger(gcStatisticsCollector), interval,interval, TimeUnit.SECONDS);

        (new LeakingClass())
                .setStartSize(2_000_000)
                .setFinalSize(20_000_000)
                .setSizeIncrement(200_000)
                .setRecreatedObjectsRatio(.5)
                .setSleepTime(2500)
                .setDoLogging(false)
                .run();
    }

}
