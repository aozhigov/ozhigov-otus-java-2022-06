package ru.otus.listener.homework;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorExceptionEveryEvenSeconds;
import ru.otus.processor.homework.ProcessorExceptionEveryEvenSeconds.ThrowExceptionEveryEvenSeconds;

/**
 *
 *
 * @author aozhigov
 * @since 11/15/22
 */
public class ProcessorExceptionEveryEvenSecondsTest {
    private static class MyClock extends Clock
    {

        @Override
        public ZoneId getZone() {
            return Clock.systemDefaultZone().getZone();
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return null;
        }

        @Override
        public Instant instant() {
            return Instant.ofEpochMilli(2);
        }
    }
    @Test
    void exceptionEveryEvenSeconds() {
        ProcessorExceptionEveryEvenSeconds processor = new ProcessorExceptionEveryEvenSeconds(new MyClock());
        assertThatThrownBy(() -> processor.process((new Message.Builder(12).build())))
                .isInstanceOf(ThrowExceptionEveryEvenSeconds.class);
    }
}