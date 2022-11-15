package ru.otus;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 *
 * @author aozhigov
 * @since 9/22/22
 */
public class ATMMachineImpl implements ATMMachine {
    private final Map<Nominal, CounterCell> moneyStore = new EnumMap<>(Nominal.class);

    public ATMMachineImpl(List<Banknote> initialBanknotes) {
        addBanknotes(initialBanknotes);
    }

    public void cashIn(List<Banknote> banknotes) {
        addBanknotes(banknotes);
    }

    public List<Banknote> cashOut(int amount) throws Exception {
        int amountCommon = getBalance();
        if (amountCommon < amount) {
            throw new Exception("Недостаточно средств");
        }

        return split(amount).stream()
                .map(it -> moneyStore.get(Nominal.of(it)).getBanknote())
                .collect(Collectors.toList());
    }

//    public int getBalance() {
//        return moneyStore.entrySet().stream()
//                .mapToInt(e -> e.getKey().getValue() * e.getValue().getCount()).sum();
//    }

    public int getBalance()
    {
        int balance = 0;
        for (Entry<Nominal, CounterCell> entry : moneyStore.entrySet())
        {
            balance += entry.getKey().getValue() * entry.getValue().getCount();
        }
        return balance;
    }

    private void addBanknotes(List<Banknote> banknotes) {
        Map<Nominal, List<Banknote>> byNominal = banknotes.stream()
                .collect(Collectors.groupingBy(Banknote::nominal));

        for (Entry<Nominal, List<Banknote>> entry : byNominal.entrySet()) {
            moneyStore.compute(entry.getKey(), (k, cell) -> {
                if (cell == null) {
                    return new CounterCell(entry.getKey(), entry.getValue());
                } else {
                    cell.addBanknotes(entry.getValue());
                    return cell;
                }
            });
        }
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