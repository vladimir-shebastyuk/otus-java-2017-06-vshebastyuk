package ru.otus.l41;


import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс собирающий информацию по сборке мусора
 */
public class GcStatisticsCollector implements NotificationListener
{
    private GarbageCollectionEventHandler garbageCollectionEventHandler;

    private long totalSpentTime = 0;
    private Map<String,StatEntry> spentTimeByGarbageCollector = new HashMap<>();

    public GcStatisticsCollector(){
        reset();
        this.registerGCLogger(this);
    }

    /**
     * @param garbageCollectionEventHandler обработчик события сборки мусора. Можно использовать для вывода доп. информации
     */
    public GcStatisticsCollector(GarbageCollectionEventHandler garbageCollectionEventHandler){
        reset();
        this.garbageCollectionEventHandler = garbageCollectionEventHandler;
        this.registerGCLogger(this);
    }

    /**
     * Сбрасывает счетчики потраченного времени и количество срабатываний
     */
    public synchronized void reset(){
        this.totalSpentTime = 0;
        spentTimeByGarbageCollector.clear();
    }

    /**
     * Возвращаем данные с собранной статистикой
     * @return таблица со статистикой по сборщикам мусора. Возвращается полная копия данных, поэтому её можно безопасно использовать в другом потоке
     */
    public synchronized Map<String,StatEntry> getCurrentStatistics(){
        HashMap<String,StatEntry> newHashMap = new HashMap<>();

        try {
            for (Map.Entry<String,StatEntry> kvp: this.spentTimeByGarbageCollector.entrySet()) {
                newHashMap.put(kvp.getKey(), (StatEntry) kvp.getValue().clone());
            }
        }catch (CloneNotSupportedException ex){

        }

        return newHashMap;
    }

    /**
     * Регистрируем данные по сборке мусора
     * @param gcName Имя/ключ сборщика мусора
     * @param duration Длительность сборки
     */
    private synchronized void recordGarbageCollection(String gcName, long duration){
        totalSpentTime+=duration;

        StatEntry statEntry = spentTimeByGarbageCollector.computeIfAbsent(gcName, k -> new StatEntry());

        statEntry.spentTime += duration;
        statEntry.invokedCount++;
    }

    private void registerGCLogger(NotificationListener listener){
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());

            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            emitter.addNotificationListener(listener, null, null);
        }
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                String gcName = info.getGcName();
                long duration = info.getGcInfo().getDuration();

                this.recordGarbageCollection(gcName,duration);

                //если задан пользовательский хендлер для отслеживания сборок, отдем ему
                if(this.garbageCollectionEventHandler != null) {
                    garbageCollectionEventHandler.handleGarbageCollectionEvent(
                            gcName,
                            info.getGcAction(),
                            info.getGcInfo().getId(),
                            info.getGcCause(),
                            duration
                    );
                }
            }
        }
    }

    /**
     * Запись со статистикой сборщика мусора
     */
    public static class StatEntry implements Cloneable{
        public long spentTime = 0;
        public long invokedCount = 0;

        @Override
        public Object clone() throws CloneNotSupportedException {
            StatEntry newStatEntry = new StatEntry();
            newStatEntry.invokedCount = this.invokedCount;
            newStatEntry.spentTime = this.spentTime;

            return newStatEntry;
        }
    }

    /**
     * Интерфейс обработчика события сборки мусора
     */
    public interface GarbageCollectionEventHandler{
        void handleGarbageCollectionEvent(String gcName, String gcType, long id, String cause, long duration);
    }

    public void setGarbageCollectionEventHandler(GarbageCollectionEventHandler garbageCollectionEventHandler) {
        this.garbageCollectionEventHandler = garbageCollectionEventHandler;
    }
}
