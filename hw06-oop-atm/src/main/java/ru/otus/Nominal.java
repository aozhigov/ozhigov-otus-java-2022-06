package ru.otus;

import java.util.Arrays;

/**
 *
 *
 * @author aozhigov
 * @since 9/22/22
 */
public enum Nominal {

    FIFTY(50),

    ONE_HUNDRED(100),

    FIVE_HUNDRED(500),

    THOUSAND(1000),

    TWO_THOUSAND(2000),

    FIVE_THOUSAND(5000);

    public static Nominal of(int value) {
        return Arrays.stream(Nominal.values())
                .filter(it -> it.value == value)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Нет подходящего номинала: " + value));
    }

    private final int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}