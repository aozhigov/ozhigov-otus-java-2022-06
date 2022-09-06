package ru.otus.runner;

import java.util.concurrent.atomic.AtomicInteger;

public class TestStatistics {
    private final AtomicInteger successes = new AtomicInteger(0);
    private final AtomicInteger errors = new AtomicInteger(0);

    public void addError() {
        errors.getAndIncrement();
    }

    public void addSuccess() {
        successes.getAndIncrement();
    }

    public int getErrors() {
        return errors.get();
    }

    public int getSuccesses() {
        return successes.get();
    }
}