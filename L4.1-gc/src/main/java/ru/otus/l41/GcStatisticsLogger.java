package ru.otus.l41;


import java.util.Calendar;
import java.util.Map;

/**
 * Класс логирующий данные по сборке мусора
 */
public class GcStatisticsLogger implements Runnable{
    private GcStatisticsCollector gcStatisticsCollector;
    private Calendar startCalendar;
    private Calendar previousRun;
    private Map<String, GcStatisticsCollector.StatEntry> previousStatData;

    public GcStatisticsLogger(GcStatisticsCollector gcStatisticsCollector) {
        this.gcStatisticsCollector = gcStatisticsCollector;
        previousRun = Calendar.getInstance();
        startCalendar = Calendar.getInstance();
    }

    private long getPreviousGcInvokedCount(String key){
        if(this.previousStatData != null && this.previousStatData.containsKey(key)){
            return this.previousStatData.get(key).invokedCount;
        }else{
            return 0L;
        }
    }
    private long getPreviousGcSpentTime(String key){
        if(this.previousStatData != null && this.previousStatData.containsKey(key)){
            return this.previousStatData.get(key).spentTime;
        }else{
            return 0L;
        }
    }

    @Override
    public void run() {
        Calendar currentCalendar = Calendar.getInstance();

        reportHeader();

        Map<String, GcStatisticsCollector.StatEntry> currentStatData =
                gcStatisticsCollector.getCurrentStatistics();

        /* Рапортуем статистику за последний интервал */
        long currentIntervalMs = ( currentCalendar.getTimeInMillis() - this.previousRun.getTimeInMillis() );
        reportCurrentIntervalStats(currentStatData, currentIntervalMs);

        /* Фиксируем состояния */
        this.previousStatData = currentStatData;
        this.previousRun = currentCalendar;

        /* Рапоратуем статистику за весь период */
        reportTotalStats(currentStatData);

    }

    private void reportHeader() {
        System.out.println();
        System.out.println(
                String.format("[ %1$tH:%1$tM:%1$tS ] - garbage collection statistics",Calendar.getInstance())
        );
    }

    private void reportCurrentIntervalStats(Map<String, GcStatisticsCollector.StatEntry> currentStatData, double currentIntervalMs) {
        long currentIntervalS = Math.round(currentIntervalMs / 1000.0);
        System.out.println(
                String.format("\t[For last %1$d seconds]", currentIntervalS)
        );

        long currentRunSpentTime = 0;

        for (Map.Entry<String, GcStatisticsCollector.StatEntry> entry: currentStatData.entrySet()) {
            long invokedCount = entry.getValue().invokedCount - this.getPreviousGcInvokedCount(entry.getKey());
            long spentTime = entry.getValue().spentTime - this.getPreviousGcSpentTime(entry.getKey());

            System.out.println("\t\t" + entry.getKey() + " was executed " + invokedCount + " times and spent " + spentTime + " ms");
            currentRunSpentTime += spentTime;
        }

        if(currentRunSpentTime > 0.0) {
            double gcCollectionPercentage = Math.min(100.0, (((double) currentRunSpentTime) / currentIntervalMs * 100.0));

            System.out.println(String.format("\t\t%.2f%% of time was spent on garbage collecting", gcCollectionPercentage));
        }
    }

    private void reportTotalStats(Map<String, GcStatisticsCollector.StatEntry> currentStatData) {
        System.out.println("\t[Total]");
        long totalRunSpentTime = 0;

        for (Map.Entry<String, GcStatisticsCollector.StatEntry> entry: currentStatData.entrySet()) {
            long invokedCount = entry.getValue().invokedCount;
            long spentTime = entry.getValue().spentTime;

            System.out.println("\t\t" + entry.getKey() + " was executed " + invokedCount + " times and spent " + spentTime + " ms");

            totalRunSpentTime += entry.getValue().spentTime;
        }

        if(totalRunSpentTime > 0.0) {
            long totalIntervalMs = ( this.previousRun.getTimeInMillis() - this.startCalendar.getTimeInMillis() );
            double gcCollectionPercentage = Math.min(100.0, (((double) totalRunSpentTime) / totalIntervalMs * 100.0));

            System.out.println(String.format("\t\t%.2f%% of time was spent on garbage collecting", gcCollectionPercentage));
        }
    }


}
