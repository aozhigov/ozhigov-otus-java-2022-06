package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    public static ObjectForMessage cloneObjectForMessage(ObjectForMessage objectForMessage) {
        ObjectForMessage clone = new ObjectForMessage();

        if (objectForMessage.getData() != null) {
            clone.setData(List.copyOf(objectForMessage.getData()));
        }

        return clone;
    }
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
