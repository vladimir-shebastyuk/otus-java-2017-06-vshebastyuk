package ru.otus.l71.atm.billsdispensestrategies;

import ru.otus.l71.atm.AtmCassette;
import ru.otus.l71.exceptions.AtmException;

import java.util.Arrays;

/**
 *
 */
public class MinimumBillsDispenseStrategy implements BillsDispenseStrategy {
    @Override
    public boolean dispense(AtmCassette[] cassettes, long amountToDispense) throws AtmException {
        AtmCassette[] sortedCassettes = Arrays.copyOf(cassettes,cassettes.length);
        Arrays.sort(sortedCassettes, (x,y) -> -1 * Long.compare(x.getBillDenomination(),y.getBillDenomination()));

        long lastAmountToDispense = amountToDispense;

        long[] billToDispenseForCassettes = new long[sortedCassettes.length];

        for (int i = 0;i<sortedCassettes.length;i++) {
            AtmCassette cassette = sortedCassettes[i];
            long billsCountToDispense = Math.min(lastAmountToDispense / cassette.getBillDenomination(), cassette.getBillsCount());

            billToDispenseForCassettes[i] = billsCountToDispense;

            lastAmountToDispense -= billsCountToDispense * cassette.getBillDenomination();
        }

        if(lastAmountToDispense == 0){
            for (int i = 0;i<sortedCassettes.length;i++) {
                AtmCassette cassette = sortedCassettes[i];
                cassette.dispenseBills(billToDispenseForCassettes[i]);
            }

            return true;
        }else{
            return false;
        }
    }
}
