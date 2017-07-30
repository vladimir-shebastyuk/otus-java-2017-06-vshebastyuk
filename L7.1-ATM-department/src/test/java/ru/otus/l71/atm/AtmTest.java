package ru.otus.l71.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.otus.l71.BalanceChangeObserver;
import ru.otus.l71.atm.billsdispensestrategies.MinimumBillsDispenseStrategy;
import ru.otus.l71.exceptions.AtmException;

/**
 *
 */
public class AtmTest {
    private Atm atm;

    @Before
    public void setUp() throws AtmException {
        this.atm = new Atm(
                new MinimumBillsDispenseStrategy()
        );

        this.atm.setCassettes(
                new AtmCassette[]{
                    new AtmCassette(100,5),
                    new AtmCassette(200,5),
                    new AtmCassette(500,5),
                    new AtmCassette(1000,5)}
                );
    }

    @Test
    public void dispenseAmount() throws Exception {

        atm.dispenseAmount(500);

        Assert.assertEquals(8500, atm.getBalance());

        atm.dispenseAmount(1300);

        Assert.assertEquals(7200, atm.getBalance());
    }

    @Test
    public void dispenseAmountGreaterThanAtmHas() throws Exception {
        Assert.assertEquals(9000,atm.getBalance());

        Assert.assertFalse(atm.dispenseAmount(9500));

        Assert.assertEquals(9000,atm.getBalance());
    }

    @Test
    public void getBalance() throws Exception {
        this.atm.setCassettes(
                new AtmCassette[]{
                        new AtmCassette(100,5),
                        new AtmCassette(200,5),
                        new AtmCassette(500,5),
                        new AtmCassette(1000,5)}
        );

        Assert.assertEquals(9000, atm.getBalance());

        this.atm.setCassettes(
                new AtmCassette[]{
                        new AtmCassette(100,2),
                        new AtmCassette(200,1),
                        new AtmCassette(500,5),
                        new AtmCassette(1000,5)}
        );

        Assert.assertEquals(7900, atm.getBalance());

        this.atm.setCassettes(
                new AtmCassette[]{
                        new AtmCassette(100,0),
                        new AtmCassette(200,0),
                        new AtmCassette(500,0),
                        new AtmCassette(1000,0)}
        );

        Assert.assertEquals(0, atm.getBalance());

        this.atm.setCassettes(
                new AtmCassette[]{
                }
        );

        Assert.assertEquals(0, atm.getBalance());
    }

    @Test
    public void saveRestoreState() throws Exception {
        this.atm.saveState();

        Assert.assertEquals(9000, this.atm.getBalance());

        this.atm.dispenseAmount(5000);

        Assert.assertEquals(4000, this.atm.getBalance());

        this.atm.restoreState();

        Assert.assertEquals(9000, this.atm.getBalance());
    }

    @Test
    public void balanceChangeObservableTest() throws Exception{
        BalanceChangeObserver observer = Mockito.mock(BalanceChangeObserver.class);

        this.atm.watchBalanceChange(observer);

        Mockito.verify(observer, Mockito.never()).balanceChanged();

        //Тестируем отсутсвие вызова при не изменении баланса
        Assert.assertFalse(this.atm.dispenseAmount(50));
        Mockito.verify(observer, Mockito.never()).balanceChanged();

        //Тестируем вызов обхервера при изменении баланса
        Assert.assertTrue(this.atm.dispenseAmount(100));
        Mockito.verify(observer, Mockito.atLeastOnce()).balanceChanged();

        //теструем вызов для двух обзерверов
        Mockito.reset(observer);
        BalanceChangeObserver observer2 = Mockito.mock(BalanceChangeObserver.class);
        this.atm.watchBalanceChange(observer2);

        Mockito.verify(observer, Mockito.never()).balanceChanged();
        Mockito.verify(observer2, Mockito.never()).balanceChanged();

        Assert.assertTrue(this.atm.dispenseAmount(100));
        Mockito.verify(observer, Mockito.atLeastOnce()).balanceChanged();
        Mockito.verify(observer2, Mockito.atLeastOnce()).balanceChanged();

        //Тестируем отвязку
        Mockito.reset(observer);
        Mockito.reset(observer2);
        this.atm.unwatchBalanceChange(observer);

        Mockito.verify(observer, Mockito.never()).balanceChanged();
        Mockito.verify(observer2, Mockito.never()).balanceChanged();

        Assert.assertTrue(this.atm.dispenseAmount(100));
        Mockito.verify(observer, Mockito.never()).balanceChanged();
        Mockito.verify(observer2, Mockito.atLeastOnce()).balanceChanged();
    }

}