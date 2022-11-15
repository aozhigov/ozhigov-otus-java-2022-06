package ru.otus;

import java.util.List;

/**
 *
 *
 * @author aozhigov
 * @since 11/15/22
 */
public interface ATMMachine {
    void cashIn(List<Banknote> banknotes);

    List<Banknote> cashOut(int amount) throws Exception;

    int getBalance();
}