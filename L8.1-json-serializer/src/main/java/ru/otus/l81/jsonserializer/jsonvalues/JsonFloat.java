package ru.otus.l81.jsonserializer.jsonvalues;

/**
 *
 */
public class JsonFloat implements JsonValue {
    private float value;

    JsonFloat(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }
}
