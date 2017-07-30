package ru.otus.l71;

/**
 * Узел иерарихии банкоматов
 */
public interface AtmHierarchyNode extends StateRestorable,BalanceChangeObservable{
    long getBalance();

    void addChild(AtmHierarchyNode node);
    void removeChild(AtmHierarchyNode node);
}
