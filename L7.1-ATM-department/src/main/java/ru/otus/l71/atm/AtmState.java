package ru.otus.l71.atm;

import ru.otus.l71.exceptions.AtmException;

/**
 * Состояние банкомата для последующего восстановления (Memento)
 */
public class AtmState {
    private AtmCassette[] cassettes;

    public AtmState(AtmCassette[] cassettes) throws AtmException {
        this.cassettes = createCopy(cassettes);
    }

    /**
     * Возврящает массив кассет банкомата. Возвращается копия внутренного массива,
     * чтобы массив и объекты внутри можно было изменять.
     * @return Копия массив сассет банкомата
     * @throws AtmException
     */
    public AtmCassette[] getCassettes() throws AtmException {
        return createCopy(this.cassettes);
    }

    private AtmCassette[] createCopy(AtmCassette[] original) throws AtmException {
        AtmCassette[] copy = new AtmCassette[original.length];

        for (int i=0;i<original.length;i++) {
            copy[i] = new AtmCassette(original[i].getBillDenomination(),original[i].getBillsCount());
        }

        return copy;
    }
}
