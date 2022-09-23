package ru.otus;

import ru.otus.annotations.LogProxyObject;
import ru.otus.calculators.Calculator;
import ru.otus.calculators.CalculatorImpl;

public class Main {
    public static void main(String[] args) {
        final Calculator addition = LogProxyObject.getInstance(new CalculatorImpl());

        addition.add(1, 1);
        addition.abs(-8);
        addition.tan(5, 6, 9);
    }
}