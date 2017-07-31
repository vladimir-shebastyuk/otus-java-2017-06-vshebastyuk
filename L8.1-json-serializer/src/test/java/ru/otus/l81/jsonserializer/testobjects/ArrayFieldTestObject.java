package ru.otus.l81.jsonserializer.testobjects;

/**
 *
 */
public class ArrayFieldTestObject {
    private int[] intArrayField = new int[] {1,2,3,4,5,6};
    private long[] longArrayField = new long[] {1L,2L,3L,4L,5L,6L};
    private String[] stringArrayField = new String[] {"1","a","1b","1c","1d"};
    private Object[] objectArrayField = new Object[] {1,"a","1b",new BooleanFieldTestObject(), new ObjectFieldTestObject()};
}
