package ru.otus.l81.jsonserializer;

import com.google.gson.Gson;
import org.junit.Assert;

/**
 * Created by jstingo on 01.08.2017.
 */
public class BaseJsonSerializerTest {
    /**
     * Сравнивает серилаизацию тестируемым объектом с сериализацией в JSON производимой
     * библиотекой GSON
     * @param object Объект для сериализации
     * @throws AssertionError В случае ошибки при сравнении выкидывает стандартнй эксепшн о провале Assert
     */
    protected void assertEqualsWithGsonSerialization(Object object) throws Exception{
        String referenceString = (new Gson()).toJson(object);
        Assert.assertEquals(referenceString,JsonSerializer.objectToJson(object));
    }
}
