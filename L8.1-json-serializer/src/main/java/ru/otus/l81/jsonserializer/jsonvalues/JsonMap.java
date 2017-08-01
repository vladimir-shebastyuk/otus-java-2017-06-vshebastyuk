package ru.otus.l81.jsonserializer.jsonvalues;

import ru.otus.l81.jsonserializer.JsonSerializerException;
import ru.otus.l81.jsonserializer.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class JsonMap implements JsonValue {
    private static final String OPEN_BRACE = "{";
    private static final String FIELD_TEMPLATE = "\"%s\":%s";
    private static final String FIELD_SEPARATOR = ",";
    private static final String CLOSE_BRACE = "}";

    protected LinkedHashMap<String,JsonValue> fields = new LinkedHashMap<>();

    protected JsonMap() {
    }

    public JsonMap(Map map) throws JsonSerializerException {
        for(Object entryObj: map.entrySet()){
            Map.Entry entry = (Map.Entry)entryObj;

            this.put(
                    entry.getKey().toString(),
                    JsonValueFactory.createFromObject(entry.getValue())
            );
        }
    }

    protected void put(String key, JsonValue value){
        if(!(value instanceof JsonNull)){ //поля с null значениями не сохраняем в JSON
            this.fields.put(key,value);
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(OPEN_BRACE);

        boolean first = true;
        for(Map.Entry<String,JsonValue> entry:fields.entrySet()){
            if(!first){
                sb.append(FIELD_SEPARATOR);
            }else{
                first=false;
            }

            sb.append(String.format(FIELD_TEMPLATE,entry.getKey(),entry.getValue().toString()));
        }

        sb.append(CLOSE_BRACE);

        return sb.toString();
    }

}
