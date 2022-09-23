package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

/**
 *
 *
 * @author aozhigov
 * @since 9/23/22
 */
public class ProcessorChangeField implements Processor {
    @Override
    public Message process(Message message) {
        var currentField11 = message.getField11();
        var currentField12 = message.getField12();

        return message.toBuilder()
                .field11(currentField12)
                .field12(currentField11)
                .build();
    }
}