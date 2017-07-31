package ru.otus.l81.jsonserializer.jsonvalues;

/**
 *
 */
public class JsonInt implements JsonValue {
    private int value;

    JsonInt(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
