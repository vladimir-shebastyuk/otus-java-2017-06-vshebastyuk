package ru.otus.l81.jsonserializer.jsonvalues;

/**
 *
 */
public class JsonLong implements JsonValue {
    private long value;

    JsonLong(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
