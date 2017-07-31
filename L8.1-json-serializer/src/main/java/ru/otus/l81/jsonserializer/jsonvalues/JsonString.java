package ru.otus.l81.jsonserializer.jsonvalues;

import ru.otus.l81.jsonserializer.JsonUtils;

/**
 *
 */
public class JsonString implements JsonValue {
    private String value;

    JsonString(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return JsonUtils.quote(value);
    }
}
