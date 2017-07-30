package ru.otus.l71;

import ru.otus.l71.atm.Atm;
import ru.otus.l71.atm.AtmState;

import java.util.ArrayList;
import java.util.List;

/**
 * Департамент банкоматов
 */
public class AtmDepartment implements AtmHierarchyNode, BalanceChangeObserver{
    private Long cachedBalance;
    private List<AtmHierarchyNode> nodes;
    private List<BalanceChangeObserver> observers;

    public AtmDepartment() {
        this.nodes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    @Override
    public void saveState() {
        this.nodes.forEach(AtmHierarchyNode::saveState);
    }

    @Override
    public boolean restoreState() {
        return this.nodes.stream()
                .map(AtmHierarchyNode::restoreState)
                .reduce(true, (x,y) -> y & x);
    }

    @Override
    public long getBalance() {
        if(this.cachedBalance == null){
            this.cachedBalance = 0L;
            for (AtmHierarchyNode node: this.nodes){
                this.cachedBalance += node.getBalance();
            }
        }

        return this.cachedBalance;
    }

    @Override
    public void addChild(AtmHierarchyNode node) {
        if(!this.nodes.contains(node)){
            this.nodes.add(node);
            node.watchBalanceChange(this);
            this.balanceChanged();
        }
    }

    @Override
    public void removeChild(AtmHierarchyNode node) {
        if(this.nodes.contains(node)){
            this.nodes.remove(node);
            node.unwatchBalanceChange(this);
            this.balanceChanged();
        }
    }

    @Override
    public void balanceChanged() {
        this.cachedBalance = null;

        this.observers.forEach(BalanceChangeObserver::balanceChanged);
    }

    @Override
    public void watchBalanceChange(BalanceChangeObserver observer) {
        if(!this.observers.contains(observer)){
            this.observers.add(observer);
        }
    }

    @Override
    public void unwatchBalanceChange(BalanceChangeObserver observer) {
        if(this.observers.contains(observer)){
            this.observers.remove(observer);
        }
    }
}
