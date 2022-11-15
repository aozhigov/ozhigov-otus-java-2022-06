package ru.otus.processor.homework;

import java.time.Clock;
import java.time.LocalTime;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

/**
 *
 *
 * @author aozhigov
 * @since 9/23/22
 */
public class ProcessorExceptionEveryEvenSeconds implements Processor {

    public static class ThrowExceptionEveryEvenSeconds extends RuntimeException {
        public ThrowExceptionEveryEvenSeconds(int currentSecond) {
            super("Current second is: " + currentSecond);
        }
    }

    private final Clock clock;

    public ProcessorExceptionEveryEvenSeconds(Clock clock) {
        this.clock = clock;
    }

    public ProcessorExceptionEveryEvenSeconds() {
        this(Clock.systemDefaultZone());
    }

    @Override
    public Message process(Message message) {
        int currentSecond = LocalTime.now(clock).getSecond();
        if (currentSecond % 2 == 0) {
            throw new ThrowExceptionEveryEvenSeconds(currentSecond);
        }
        return message.toBuilder().build();
    }
}