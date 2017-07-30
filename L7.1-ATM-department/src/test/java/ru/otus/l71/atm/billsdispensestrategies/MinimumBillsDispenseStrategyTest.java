package ru.otus.l71.atm.billsdispensestrategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l71.atm.AtmCassette;
import ru.otus.l71.atm.CassetteArrayTester;
import ru.otus.l71.atm.billsdispensestrategies.BillsDispenseStrategy;
import ru.otus.l71.atm.billsdispensestrategies.MinimumBillsDispenseStrategy;
import ru.otus.l71.exceptions.AtmException;

/**
 *
 */
public class MinimumBillsDispenseStrategyTest {
    private BillsDispenseStrategy strategy;
    private AtmCassette[] cassettes;

    @Before
    public void setUp() throws AtmException {
        this.strategy = new MinimumBillsDispenseStrategy();
        cassettes = new AtmCassette[]{
                new AtmCassette(100,5),
                new AtmCassette(200,5),
                new AtmCassette(500,5),
                new AtmCassette(1000,5),
        };

    }

    @Test
    public void dispenseAmountLessThenMinimumDenom() throws Exception {
        Assert.assertFalse(strategy.dispense(cassettes,5));

        new CassetteArrayTester(cassettes)
                .assertBillsCount(100,5)
                .assertBillsCount(200,5)
                .assertBillsCount(500,5)
                .assertBillsCount(1000,5);
    }

    @Test
    public void dispenseSingleSmallestBill() throws AtmException {
        Assert.assertTrue(strategy.dispense(cassettes,100));

        new CassetteArrayTester(cassettes)
                .assertBillsCount(100,4)
                .assertBillsCount(200,5)
                .assertBillsCount(500,5)
                .assertBillsCount(1000,5);
    }

    @Test
    public void dispenseSingleLargestBill() throws AtmException {
        Assert.assertTrue(strategy.dispense(cassettes,1000));

        new CassetteArrayTester(cassettes)
                .assertBillsCount(100,5)
                .assertBillsCount(200,5)
                .assertBillsCount(500,5)
                .assertBillsCount(1000,4);
    }

    @Test
    public void dispenseByDifferentBills() throws AtmException {
        Assert.assertTrue(strategy.dispense(cassettes,2800));

        new CassetteArrayTester(cassettes)
                .assertBillsCount(100,4)
                .assertBillsCount(200,4)
                .assertBillsCount(500,4)
                .assertBillsCount(1000,3);
    }

    @Test
    public void dispenseNotEnough() throws AtmException {
        Assert.assertFalse(strategy.dispense(cassettes,9100));

        cassettes = new AtmCassette[]{
                new AtmCassette(100,1),
                new AtmCassette(300,1),
                new AtmCassette(500,1),
                new AtmCassette(1000,1),
        };

        Assert.assertFalse(strategy.dispense(cassettes,1700));
    }



}