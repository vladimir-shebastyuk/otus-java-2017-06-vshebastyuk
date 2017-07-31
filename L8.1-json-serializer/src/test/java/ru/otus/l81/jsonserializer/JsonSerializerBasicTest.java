package ru.otus.l81.jsonserializer;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import ru.otus.l81.jsonserializer.testobjects.*;

/**
 *
 */
public class JsonSerializerBasicTest extends BaseJsonSerializerTest{
    @Test
    public void inheritedObject() throws Exception {
        Object object = new InheritedFieldObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void textFieldSerializationTest() throws Exception {
        Object object = new StringFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void intFieldsSerializationTest() throws Exception {
        Object object = new IntFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void longFieldsSerializationTest() throws Exception {
        Object object = new LongFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void floatFieldsSerializationTest() throws Exception {
        Object object = new FloatFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void doubleFieldsSerializationTest() throws Exception {
        Object object = new DoubleFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void objectFieldsSerializationTest() throws Exception {
        Object object = new ObjectFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void arrayFieldsSerializationTest() throws Exception {
        Object object = new ArrayFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void collectionFieldsSerializationTest() throws Exception {
        Object object = new CollectionFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void mapFieldsSerializationTest() throws Exception {
        Object object = new MapFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }

    @Test
    public void transientFieldsSerializationTest() throws Exception {
        Object object = new TransientFieldTestObject();
        assertEqualsWithGsonSerialization(object);
    }
}