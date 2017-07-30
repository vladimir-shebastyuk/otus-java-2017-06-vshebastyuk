package ru.otus.l71;

import org.junit.Assert;
import ru.otus.l71.atm.Atm;
import ru.otus.l71.atm.AtmCassette;
import ru.otus.l71.atm.billsdispensestrategies.MinimumBillsDispenseStrategy;
import ru.otus.l71.exceptions.AtmException;


public class AtmTestUtils {
    private AtmTestUtils() {
    }

    public static Atm createAtmWithBalance(long balance) throws AtmException {
        Atm atm = new Atm(
                new MinimumBillsDispenseStrategy()
        );

        atm.setCassettes(
                new AtmCassette[]{
                        new AtmCassette(1, Math.floorMod(balance, 100)),
                        new AtmCassette(100, Math.floorDiv(balance, 100))
                }
        );

        Assert.assertEquals(balance, atm.getBalance());

        return atm;
    }

}
