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
    private final List<Banknote> banknotes = new ArrayList<>();

    private final AtomicInteger sum;

    public CounterCell(List<Banknote> banknotes) {
        this.banknotes.addAll(banknotes);
        sum = new AtomicInteger(getSum(banknotes));
    }

    public boolean addBanknote(Banknote banknote) {
        sum.addAndGet(banknote.nominal().getValue());
        return banknotes.add(banknote);
    }

    public boolean addBanknotes(List<Banknote> banknotes) {
        sum.addAndGet(getSum(banknotes));
        return this.banknotes.addAll(banknotes);
    }

    public int getAmount() {
        return sum.get();
    }

    private static int getSum(List<Banknote> listBanknotes) {
        return listBanknotes.stream().mapToInt(e -> e.nominal().getValue()).sum();
    }

    public Banknote getBanknote() {
        if (banknotes.isEmpty()) {
            throw new RuntimeException("Недостаточно денег в ячейке");
        }
        int idx = banknotes.size() - 1;
        Banknote banknote = banknotes.get(idx);
        banknotes.remove(banknote);
        sum.addAndGet(-banknote.nominal().getValue());
        return banknote;
    }
}