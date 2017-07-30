package ru.otus.l71.atm.billsdispensestrategies;

import ru.otus.l71.atm.AtmCassette;
import ru.otus.l71.exceptions.AtmException;

/**
 *
 */
public interface BillsDispenseStrategy {
    boolean dispense(AtmCassette[] cassettes, long amountToDispense) throws AtmException;
}
