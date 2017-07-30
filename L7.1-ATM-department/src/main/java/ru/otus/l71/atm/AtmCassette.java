package ru.otus.l71.atm;

import ru.otus.l71.exceptions.AtmException;

/**
 * Кассета с купюрами одного номинала
 */
public class AtmCassette {
    private long billDenomination;
    private long billsCount;

    public AtmCassette(long billDenomination) throws AtmException {
        if(billDenomination <= 0){
            throw new AtmException("Denomination of bills should be greater than zero");
        }
        this.billDenomination = billDenomination;
    }

    public AtmCassette(long billDenomination, long billsCount) throws AtmException {
        if(billDenomination <= 0){
            throw new AtmException("Denomination of bills should be greater than zero");
        }
        this.billDenomination = billDenomination;
        this.billsCount = billsCount;
    }

    public long getBillDenomination() {
        return billDenomination;
    }

    public long getBillsCount() {
        return billsCount;
    }

    public void dispenseBills(long billsCount) throws AtmException {
        if(billsCount > this.billsCount){
            throw new AtmException("Cannot dispense more bills than there are in the cassette");
        }

        this.billsCount -= billsCount;
    }

    public long getMoneyAmount(){
        return this.billDenomination * this.billsCount;
    }
}
