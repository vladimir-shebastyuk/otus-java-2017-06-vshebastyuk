package ru.otus.l91.jdbc.simpleorm;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.l91.jdbc.simpleorm.testentity.*;

import java.util.Map;

/**
 *
 */
public class SimpleOrmEntityHelperTest {
    @Test
    public void isMappedSuperclass() throws Exception {
        MappedSuperclassExample mappedSuperclassExample =
                new MappedSuperclassExample(0);

        Assert.assertTrue(SimpleOrmEntityHelper.isMappedSuperclass(mappedSuperclassExample));

        Object object = new Object();

        Assert.assertFalse(SimpleOrmEntityHelper.isMappedSuperclass(object));
    }

    @Test
    public void isValidEntity() throws Exception {
        Assert.assertTrue(SimpleOrmEntityHelper.isValidEntity(
                EntityExample.class
        ));

        Assert.assertFalse("Entity without @Entity annotation should not be valid", SimpleOrmEntityHelper.isValidEntity(
                NonValidEntityNoAnnotation.class
        ));

        Assert.assertFalse("Entity without default constructor should not be valid", SimpleOrmEntityHelper.isValidEntity(
                NonValidEntityNoDefaultConstructor.class
        ));

        Assert.assertFalse("Entity without id field should not be valid", SimpleOrmEntityHelper.isValidEntity(
                NonValidEntityNoIdField.class
        ));
    }

    @Test
    public void getTableName() throws Exception {
        EntityExample tableNameByClassName = new EntityExample();

        Assert.assertEquals("entity_example", SimpleOrmEntityHelper.getTableName(tableNameByClassName.getClass()));

        EntityExampleWithChangedNames entityExampleWithChangedNames = new EntityExampleWithChangedNames();

        Assert.assertEquals("entity", SimpleOrmEntityHelper.getTableName(entityExampleWithChangedNames.getClass()));
    }

    @Test
    public void getColumnName() throws Exception {
        Assert.assertEquals("simple_name", SimpleOrmEntityHelper.getColumnName(
                EntityExampleWithChangedNames.class.getDeclaredField("simpleName")
        ));

        Assert.assertEquals("column_name_field", SimpleOrmEntityHelper.getColumnName(
                EntityExampleWithChangedNames.class.getDeclaredField("fieldWithChangedColumnName")
        ));
    }

    @Test
    public void camelCaseToSnakeCase() throws Exception {
        Assert.assertEquals("some_field_name", SimpleOrmEntityHelper.camelCaseToSnakeCase("someFieldName"));
        Assert.assertEquals("my_class_name", SimpleOrmEntityHelper.camelCaseToSnakeCase("MyClassName"));
        Assert.assertEquals("iso_test", SimpleOrmEntityHelper.camelCaseToSnakeCase("ISOTest"));
        Assert.assertEquals("start_ts", SimpleOrmEntityHelper.camelCaseToSnakeCase("StartTs"));
    }

    @Test
    public void getColumnValues() throws Exception {
        EntityExample entityExample =
                new EntityExample(1,"Name, Jr.","CEO","Transient Value");

        for(Map.Entry<String,Object> entry: SimpleOrmEntityHelper.getColumnValues(entityExample).entrySet()){
            if(entry.getKey().equals("id")){
                Assert.assertEquals(1L,entry.getValue());
            }else if(entry.getKey().equals("name")){
                Assert.assertEquals("Name, Jr.",entry.getValue());
            }else if(entry.getKey().equals("job_title")){
                Assert.assertEquals("CEO",entry.getValue());
            }else{
                Assert.fail("There should not be column " + entry.getKey());
            }
        }
    }

    @Test
    public void isTransient() throws Exception {
        Assert.assertTrue(SimpleOrmEntityHelper.isTransient(
                EntityExample.class.getDeclaredField("transientField")
        ));

        Assert.assertTrue(SimpleOrmEntityHelper.isTransient(
                EntityExample.class.getDeclaredField("transientFieldByAnnotation")
        ));

        Assert.assertFalse(SimpleOrmEntityHelper.isTransient(
                EntityExample.class.getDeclaredField("name")
        ));
    }

    @Test
    public void isIdNotSet() throws Exception {
        EntityExample entityExample = new EntityExample();

        Assert.assertTrue(SimpleOrmEntityHelper.isIdNotSet(entityExample));

        entityExample.setId(1);

        Assert.assertFalse(SimpleOrmEntityHelper.isIdNotSet(entityExample));
    }
}