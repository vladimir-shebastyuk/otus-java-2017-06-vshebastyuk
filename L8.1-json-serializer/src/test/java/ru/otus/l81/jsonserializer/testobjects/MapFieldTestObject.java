package ru.otus.l81.jsonserializer.testobjects;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MapFieldTestObject {
    private Map mapField = new HashMap();

    {
        mapField.put("1",1);
        mapField.put("a","a");
        mapField.put(3,new ObjectFieldTestObject());
    }
}
