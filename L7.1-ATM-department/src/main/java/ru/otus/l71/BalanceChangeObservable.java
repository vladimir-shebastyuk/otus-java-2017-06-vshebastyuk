package ru.otus.l71;

/**
 * Интерфейс объекта на изменение баланса которого можно подписаться
 */
public interface BalanceChangeObservable {
    void watchBalanceChange(BalanceChangeObserver observer);
    void unwatchBalanceChange(BalanceChangeObserver observer);
}
