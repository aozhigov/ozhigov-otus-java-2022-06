package ru.otus;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 *
 * @author aozhigov
 * @since 9/22/22
 */
public class ATMMachine {
    private final Map<Nominal, CounterCell> moneyStore = new EnumMap<>(Nominal.class);
    private final AtomicInteger balance;

    public ATMMachine(List<Banknote> initialBanknotes) {
        addBanknotes(initialBanknotes);
        balance = new AtomicInteger(getBalance());
    }

    public void cashIn(List<Banknote> banknotes) {
        addBanknotes(banknotes);
    }

    public List<Banknote> cashOut(int amount) throws Exception {
        int amountCommon = getBalance();
        if (amountCommon < amount) {
            throw new Exception("Недостаточно средств");
        }

        balance.addAndGet(-amount);
        return split(amount).stream()
                .map(it -> moneyStore.get(Nominal.of(it)).getBanknote())
                .collect(Collectors.toList());
    }

    private int getBalance() {
        return moneyStore.values().stream()
                .mapToInt(CounterCell::getAmount)
                .sum();
    }

    public int getBalanceValue()
    {
        return balance.get();
    }

    private void addBanknotes(List<Banknote> initialBanknotes) {
        Map<Nominal, List<Banknote>> byNominal = initialBanknotes.stream()
                .collect(Collectors.groupingBy(Banknote::nominal));

        byNominal.forEach((nominal, banknotes) -> {
            moneyStore.compute(nominal, (k, cell) -> {
                if (cell == null) {
                    return new CounterCell(banknotes);
                } else {
                    cell.addBanknotes(banknotes);
                    return cell;
                }
            });
        });
    }

    private List<Integer> split(int amount) {
        Integer[] notes = Arrays.stream(Nominal.values())
                .map(Nominal::getValue)
                .sorted(Collections.reverseOrder())
                .toArray(Integer[]::new);

        int nominalCount = Nominal.values().length;
        int[] noteCounter = new int[nominalCount];

        for (int i = 0; i < nominalCount; i++) {
            if (amount >= notes[i]) {
                noteCounter[i] = amount / notes[i];
                amount = amount - noteCounter[i] * notes[i];
            }
        }

        return IntStream.range(0, nominalCount)
                .map(i -> {
                    if (noteCounter[i] != 0) {
                        return notes[i];
                    }
                    return 0;
                })
                .boxed()
                .filter(it -> it != 0)
                .collect(Collectors.toList());
    }
}