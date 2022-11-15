package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * @author aozhigov
 * @since 9/22/22
 */
public class CounterCell {
    private final Nominal currentNominal;

    private final AtomicInteger count;

    public CounterCell(Nominal nominal, List<Banknote> banknotes) {
        this.currentNominal = nominal;
        count = new AtomicInteger(banknotes.size());
    }

    public void addBanknote(Banknote banknote) {
        assert banknote.nominal().getValue() == currentNominal.getValue();
        count.addAndGet(1);
    }

    public void addBanknotes(List<Banknote> banknotes) {
        banknotes.forEach(this::addBanknote);
    }

    public int getCount() {
        return count.get();
    }

    public Banknote getBanknote() {
        if (count.get() == 0) {
            throw new RuntimeException("Недостаточно денег в ячейке");
        }
        count.addAndGet(-1);
        return new Banknote(currentNominal);
    }

    public Nominal getCurrentNominal() {
        return currentNominal;
    }
}