package ru.otus.calculators;

import ru.otus.annotations.Log;

/**
 *
 *
 * @author aozhigov
 * @since 9/23/22
 */
public class CalculatorImpl implements Calculator {
    @Log
    @Override
    public double abs(double param1) {
        return Math.abs(param1);
    }

    @Log
    @Override
    public double add(double param1, double param2) {
        return param1 + param2;
    }

    @Log
    @Override
    public double tan(double param1, double param2, double param3) {
        return 0;
    }
}