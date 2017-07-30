package ru.otus.l71;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.otus.l71.atm.Atm;

/**
 *
 */
public class AtmDepartmentCompexHierarchyTests {

    private AtmDepartment department1;
    private Atm atm1_1;
    private Atm atm1_2;

    private AtmDepartment department2;
    private Atm atm2_1;
    private Atm atm2_2;

    private AtmDepartment rootDepartment;

    @Before
    public void setUp() throws Exception {
        department1 = new AtmDepartment();

        atm1_1 = AtmTestUtils.createAtmWithBalance(5000);
        atm1_2 = AtmTestUtils.createAtmWithBalance(10000);

        department1.addChild(atm1_1);
        department1.addChild(atm1_2);

        department2 = new AtmDepartment();

        atm2_1 = AtmTestUtils.createAtmWithBalance(3000);
        atm2_2 = AtmTestUtils.createAtmWithBalance(4000);

        department2.addChild(atm2_1);
        department2.addChild(atm2_2);

        rootDepartment = new AtmDepartment();

        rootDepartment.addChild(department1);
        rootDepartment.addChild(department2);
    }

    @Test
    public void getBalanceTest() throws Exception {
        Assert.assertEquals(22000,rootDepartment.getBalance());

        atm1_1.dispenseAmount(1000);

        Assert.assertEquals(21000,rootDepartment.getBalance());

        atm1_1.dispenseAmount(100);
        atm1_2.dispenseAmount(200);
        atm2_1.dispenseAmount(300);
        atm2_2.dispenseAmount(400);

        Assert.assertEquals(20000,rootDepartment.getBalance());
        Assert.assertEquals(13700,department1.getBalance());
        Assert.assertEquals(6300,department2.getBalance());
    }

    @Test
    public void saveRestoreState() throws Exception {
        Assert.assertEquals(22000,rootDepartment.getBalance());

        rootDepartment.saveState();

        Assert.assertEquals(22000,rootDepartment.getBalance());

        atm1_1.dispenseAmount(2000);

        Assert.assertEquals(20000,rootDepartment.getBalance());

        rootDepartment.restoreState();

        Assert.assertEquals(22000,rootDepartment.getBalance());
    }

    @Test
    public void addRemoveChild() throws Exception {
        Assert.assertEquals(22000,rootDepartment.getBalance());
        rootDepartment.removeChild(department2);

        Assert.assertEquals(15000,rootDepartment.getBalance());

        rootDepartment.addChild(department2);
        Assert.assertEquals(22000,rootDepartment.getBalance());

        //не должно быть изменений, т.к. узел уже существует
        rootDepartment.addChild(department2);
        Assert.assertEquals(22000,rootDepartment.getBalance());


        department1.removeChild(atm1_1);
        Assert.assertEquals(17000,rootDepartment.getBalance());

        department1.addChild(atm1_1);
        Assert.assertEquals(22000,rootDepartment.getBalance());
    }

    @Test
    public void balanceObserverTest() throws Exception {
        BalanceChangeObserver observer = Mockito.mock(BalanceChangeObserver.class);

        rootDepartment.watchBalanceChange(observer);

        atm1_1.dispenseAmount(500);
        Mockito.verify(observer, Mockito.times(1)).balanceChanged();
    }
}
