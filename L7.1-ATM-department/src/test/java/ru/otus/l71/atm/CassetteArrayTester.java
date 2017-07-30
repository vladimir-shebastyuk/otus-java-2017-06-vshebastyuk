package ru.otus.l71.atm;

/**
 * Вспомогательный класс для тестирования кол-ва купуюр в кассетах банкомата
 */
public class CassetteArrayTester{
    private boolean conformAllAsserts = true;
    private AtmCassette[] cassettes;

    public CassetteArrayTester(AtmCassette[] cassettes) {
        this.cassettes = cassettes;
    }

    public CassetteArrayTester assertBillsCount(long billDenomination, long billsCount){
        boolean found = false;
        for (AtmCassette cassette: this.cassettes) {
            if((cassette.getBillDenomination() == billDenomination) && (cassette.getBillsCount() == billsCount)){
                found = true;
                break;
            }
        }

        if(!found){
            throw new AssertionError("There are no " + billsCount + " bills of " + billDenomination + " denomination");
        }

        return this;
    }
}
