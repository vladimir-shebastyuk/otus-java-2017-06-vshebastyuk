package ru.otus.l81.jsonserializer;

import ru.otus.l81.jsonserializer.jsonvalues.JsonObject;
import ru.otus.l81.jsonserializer.jsonvalues.JsonValueFactory;

/**
 * Сериализиатор объектов в JSON.
 */
public class JsonSerializer {

    /**
     * Возвращает JSON по переданному объекту
     * @param object Объект для сериализиации
     * @return Строка с JSON
     * @throws JsonSerializerException
     */
    public static String objectToJson(Object object) throws JsonSerializerException {

        return JsonValueFactory.createFromObject(object).toString();
    }


}
