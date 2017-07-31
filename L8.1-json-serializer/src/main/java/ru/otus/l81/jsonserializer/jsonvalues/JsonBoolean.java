package ru.otus.l81.jsonserializer.jsonvalues;

/**
 *
 */
public class JsonBoolean implements JsonValue {
    private boolean value;

    JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
