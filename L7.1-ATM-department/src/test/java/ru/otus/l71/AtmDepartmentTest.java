package ru.otus.l71;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.otus.l71.atm.Atm;

/**
 *
 */
public class AtmDepartmentTest {

    private AtmDepartment department;
    private Atm atm1;
    private Atm atm2;

    @Before
    public void setUp() throws Exception {
        department = new AtmDepartment();

        atm1 = AtmTestUtils.createAtmWithBalance(5000);
        atm2 = AtmTestUtils.createAtmWithBalance(10000);

        department.addChild(atm1);
        department.addChild(atm2);
    }

    @Test
    public void saveRestoreState() throws Exception {
        Assert.assertEquals(15000, department.getBalance());

        department.saveState();

        Assert.assertEquals(15000, department.getBalance());

        atm1.dispenseAmount(2000);

        Assert.assertEquals(13000, department.getBalance());
    }

    @Test
    public void getBalance() throws Exception {
        Assert.assertEquals(15000, department.getBalance());

        atm1.dispenseAmount(500);

        Assert.assertEquals(14500, department.getBalance());
        Assert.assertEquals(14500, department.getBalance());

        atm2.dispenseAmount(500);

        Assert.assertEquals(14000, department.getBalance());
        Assert.assertEquals(14000, department.getBalance());

        atm1.dispenseAmount(400);
        atm2.dispenseAmount(600);

        Assert.assertEquals(13000, department.getBalance());
    }

    @Test
    public void balanceObserverTest() throws Exception {
        BalanceChangeObserver observer = Mockito.mock(BalanceChangeObserver.class);

        department.watchBalanceChange(observer);

        atm1.dispenseAmount(500);
        Mockito.verify(observer, Mockito.times(1)).balanceChanged();
    }

}