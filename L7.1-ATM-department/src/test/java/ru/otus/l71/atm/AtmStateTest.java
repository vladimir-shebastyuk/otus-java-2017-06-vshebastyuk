package ru.otus.l71.atm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class AtmStateTest {
    @Test
    public void getCassettes() throws Exception {
        AtmCassette[] cassettes = new AtmCassette[]{
                new AtmCassette(100,5),
                new AtmCassette(200,5),
                new AtmCassette(500,5),
                new AtmCassette(1000,5)
        };

        AtmState atmState = new AtmState(cassettes);

        //изменяем оригиналы
        for(AtmCassette atmCassette: cassettes){
            atmCassette.dispenseBills(1);
        }
        new CassetteArrayTester(cassettes)
                .assertBillsCount(100,4)
                .assertBillsCount(200,4)
                .assertBillsCount(500,4)
                .assertBillsCount(1000,4);

        //проверяем что не смотря на изменение оригинала сохраненные значения должня быть не изменны
        AtmCassette[] restoredAtmCassettes = atmState.getCassettes();
        new CassetteArrayTester(restoredAtmCassettes)
                .assertBillsCount(100,5)
                .assertBillsCount(200,5)
                .assertBillsCount(500,5)
                .assertBillsCount(1000,5);

        //изменяем востановленые значения
        for(AtmCassette atmCassette: restoredAtmCassettes){
            atmCassette.dispenseBills(1);
        }
        new CassetteArrayTester(restoredAtmCassettes)
                .assertBillsCount(100,4)
                .assertBillsCount(200,4)
                .assertBillsCount(500,4)
                .assertBillsCount(1000,4);

        //и повторно восстанвливаем значения и проверяем, что состояние в первоначальном виде
        new CassetteArrayTester(atmState.getCassettes())
                .assertBillsCount(100,5)
                .assertBillsCount(200,5)
                .assertBillsCount(500,5)
                .assertBillsCount(1000,5);
    }

}