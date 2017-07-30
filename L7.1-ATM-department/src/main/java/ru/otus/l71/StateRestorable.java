package ru.otus.l71;

/**
 * Интерфейс объектов, которые могут сохранять свое состояние
 */
public interface StateRestorable {
    /**
     * Сохраняет состояние объекта
     */
    void saveState();

    /**
     * Восстанавливает состояние объекта
     * @return Успешно ли прошло восстановление
     */
    boolean restoreState();
}
