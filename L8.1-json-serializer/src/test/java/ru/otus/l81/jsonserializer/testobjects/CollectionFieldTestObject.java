package ru.otus.l81.jsonserializer.testobjects;

import java.util.*;

/**
 *
 */
public class CollectionFieldTestObject {
    private ArrayList<Integer> arrayListField = new ArrayList<Integer>(Arrays.asList(new Integer[] {1,2,3,4,5,6}));
    private LinkedList<Integer> linkedListField = new LinkedList<Integer>(Arrays.asList(new Integer[] {1,2,3,4,5,6}));
    private HashSet<Object> setField = new HashSet<Object>(Arrays.asList(new Object[] {1,"a","1b",new BooleanFieldTestObject(), new ObjectFieldTestObject()}));
}
