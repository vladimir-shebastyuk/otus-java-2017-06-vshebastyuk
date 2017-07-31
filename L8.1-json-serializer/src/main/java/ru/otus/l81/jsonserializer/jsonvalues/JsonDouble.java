package ru.otus.l81.jsonserializer.jsonvalues;

/**
 *
 */
public class JsonDouble implements JsonValue {
    private double value;

    JsonDouble(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
