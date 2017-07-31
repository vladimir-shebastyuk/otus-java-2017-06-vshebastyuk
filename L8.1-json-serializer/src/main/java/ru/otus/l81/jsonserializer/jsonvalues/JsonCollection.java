package ru.otus.l81.jsonserializer.jsonvalues;

import ru.otus.l81.jsonserializer.JsonSerializerException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class JsonCollection implements JsonValue {
    private final static String OPEN_BRACE = "[";
    private final static String ITEMS_SEPARATOR = ",";
    private final static String CLOSE_BRACE = "]";

    protected List<JsonValue> value;

    protected JsonCollection() {
    }

    JsonCollection(Collection collection) throws JsonSerializerException {
       this.value = new ArrayList<>();

        for(Object object : collection){
            this.value.add(JsonValueFactory.createFromObject(object));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(OPEN_BRACE);

        boolean first = true;
        for(JsonValue jsonValue: this.value){
            if(!first){
                sb.append(ITEMS_SEPARATOR);
            }else{
                first=false;
            }

            sb.append(jsonValue.toString());
        }

        sb.append(CLOSE_BRACE);

        return sb.toString();
    }
}
