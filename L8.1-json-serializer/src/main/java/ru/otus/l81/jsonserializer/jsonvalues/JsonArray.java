package ru.otus.l81.jsonserializer.jsonvalues;

import ru.otus.l81.jsonserializer.JsonSerializerException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 */
public class JsonArray extends JsonCollection {
    JsonArray(Object array) throws JsonSerializerException {
        int length = Array.getLength(array);
        this.value = new ArrayList<>();

        for(int i = 0; i < length; i++){
            this.value.add(JsonValueFactory.createFromObject(Array.get(array,i)));
        }
    }

}
