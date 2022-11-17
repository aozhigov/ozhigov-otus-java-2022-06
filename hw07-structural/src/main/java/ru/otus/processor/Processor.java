package ru.otus.processor;

import ru.otus.model.Message;

@FunctionalInterface
public interface Processor {

    Message process(Message message);
}
