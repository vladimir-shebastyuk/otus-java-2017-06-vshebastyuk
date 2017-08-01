package ru.otus.l81.jsonserializer.jsonvalues;

import ru.otus.l81.jsonserializer.JsonSerializerException;
import ru.otus.l81.jsonserializer.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 *
 */
public class JsonObject extends JsonMap {
    private final static String OUTER_CLASS_REFERENCE_FIELD = "this$0";

    public JsonObject(Object object) throws JsonSerializerException {
        for(Field field: ReflectionHelper.getAllFields(object)){
            //добавляем все поля, кроме ссылки на внешний класс или если поле transient
            if(!field.getName().equals(OUTER_CLASS_REFERENCE_FIELD) && !Modifier.isTransient(field.getModifiers())) {
                this.put(
                        field.getName(),
                        JsonValueFactory.createFromObjectField(field, object)
                );
            }
        }
    }
}
