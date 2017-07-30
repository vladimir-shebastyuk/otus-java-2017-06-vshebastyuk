package ru.otus.l71;

import ru.otus.l71.atm.Atm;
import ru.otus.l71.atm.AtmCassette;
import ru.otus.l71.atm.billsdispensestrategies.MinimumBillsDispenseStrategy;
import ru.otus.l71.exceptions.AtmException;

/**
 *
 */
public class Main {

    public static void main(String... args) throws Exception{
        final AtmDepartment rootDepartment = new AtmDepartment();
        rootDepartment.watchBalanceChange(() -> {System.out.println("Observer | Root Department new balance: " + rootDepartment.getBalance());});

        final AtmDepartment department1 = new AtmDepartment();
        department1.watchBalanceChange(() -> {System.out.println("Observer | Department #1 new balance: " + department1.getBalance());});
        rootDepartment.addChild(department1);

        final AtmDepartment department2 = new AtmDepartment();
        department2.watchBalanceChange(() -> {System.out.println("Observer | Department #1 new balance: " + department2.getBalance());});
        rootDepartment.addChild(department2);

        final Atm atm1_1 = new Atm(new MinimumBillsDispenseStrategy());
        atm1_1.setCassettes(new AtmCassette[]{
                new AtmCassette(100,5),
                new AtmCassette(200,5),
                new AtmCassette(500,5),
                new AtmCassette(1000,5)}
                );

        department1.addChild(atm1_1);

        final Atm atm1_2 = new Atm(new MinimumBillsDispenseStrategy());
        atm1_2.setCassettes(new AtmCassette[]{
                new AtmCassette(100,5),
                new AtmCassette(200,5),
                new AtmCassette(500,5),
                new AtmCassette(1000,0)}
        );

        department1.addChild(atm1_2);

        final Atm atm2_1 = new Atm(new MinimumBillsDispenseStrategy());
        atm2_1.setCassettes(new AtmCassette[]{
                new AtmCassette(100,5),
                new AtmCassette(200,5),
                new AtmCassette(500,5),
                new AtmCassette(1000,5)}
        );

        department2.addChild(atm2_1);

        final Atm atm2_2 = new Atm(new MinimumBillsDispenseStrategy());
        atm2_2.setCassettes(new AtmCassette[]{
                new AtmCassette(100,5),
                new AtmCassette(200,5),
                new AtmCassette(500,5),
                new AtmCassette(1000,15)}
        );

        department2.addChild(atm2_2);

        System.out.println("Root Department state was saved");
        rootDepartment.saveState();

        //выдача денег
        withdraw(atm1_1,500);
        withdraw(atm1_1,330);

        System.out.println("Restoring root department state...");
        if(rootDepartment.restoreState()) System.out.println("Root department state was successfully restored");;

    }

    private static void withdraw(Atm atm, long sum){
        try {
            System.out.println("Withdrawing " + Long.toString(sum));
            if (atm.dispenseAmount(sum)) {
                System.out.println("Success! Take your money");
            }else{
                System.out.println("Cannot withdraw money, please, try later");
            }
        }catch (AtmException ex){
            System.out.println("Error during withrawing money: " + ex.getMessage());
        }
    }
}
