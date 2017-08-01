package ru.otus.l81.jsonserializer.testobjects;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class NullFieldTestObject {
    private Integer nullIntegerField = null;
    private Object nullObjectField = null;
    private Map map = new HashMap();

    {
        map.put("someNullValue",null);
    }
}
