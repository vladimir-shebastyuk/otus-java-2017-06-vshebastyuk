package ru.otus.l71.atm;

import ru.otus.l71.AtmHierarchyNode;
import ru.otus.l71.BalanceChangeObserver;
import ru.otus.l71.atm.billsdispensestrategies.BillsDispenseStrategy;
import ru.otus.l71.exceptions.AtmException;

import java.util.ArrayList;
import java.util.List;

/**
 * Банкомат
 */
public class Atm implements AtmHierarchyNode{
    private AtmCassette[] cassettes;
    private BillsDispenseStrategy billsDispenseStrategy;
    private List<BalanceChangeObserver> observers;

    private AtmState savedState;

    public Atm(BillsDispenseStrategy billsDispenseStrategy) {
        this.billsDispenseStrategy = billsDispenseStrategy;
        cassettes = new AtmCassette[0];

        this.observers = new ArrayList<>();
    }

    public void setBillsDispenseStrategy(BillsDispenseStrategy billsDispenseStrategy) {
        this.billsDispenseStrategy = billsDispenseStrategy;
    }

    public void setCassettes(AtmCassette[] cassettes) {
        this.cassettes = cassettes;
        this.notifyBalanceHasBeenChanged();
    }

    public boolean dispenseAmount(long moneyAmount) throws AtmException {
        if(this.billsDispenseStrategy.dispense(this.cassettes,moneyAmount)){
            this.notifyBalanceHasBeenChanged();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void saveState() {
        try {
            this.savedState = new AtmState(this.cassettes);
        } catch (AtmException e) {
            this.savedState = null;
        }
    }

    @Override
    public boolean restoreState() {
        try {
            if(this.savedState == null) return false;

            long oldBalance = this.getBalance();

            this.cassettes = this.savedState.getCassettes();

            if(oldBalance != this.getBalance()) this.notifyBalanceHasBeenChanged();

            return true;
        } catch (AtmException e) {
            return false;
        }
    }

    @Override
    public long getBalance(){
        long sum = 0;
        for (AtmCassette cassette : cassettes) {
            sum+= cassette.getMoneyAmount();
        }

        return sum;
    }

    @Override
    public void addChild(AtmHierarchyNode node) {

    }

    @Override
    public void removeChild(AtmHierarchyNode node) {

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

    private void notifyBalanceHasBeenChanged(){
        this.observers.forEach(BalanceChangeObserver::balanceChanged);
    }


}
