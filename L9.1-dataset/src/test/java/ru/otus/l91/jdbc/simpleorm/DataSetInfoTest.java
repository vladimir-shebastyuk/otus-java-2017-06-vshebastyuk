package ru.otus.l91.jdbc.simpleorm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l91.jdbc.simpleorm.testentity.EntityExample;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by jstingo on 22.08.2017.
 */
public class DataSetInfoTest {
    private EntityExample entityExample;
    private DataSetInfo entityExampleDataSetInfo;

    @Before
    public void setUp() throws Exception {
        entityExample = new EntityExample(1,"Some Name","Job Title","Transient field");
        entityExampleDataSetInfo = new DataSetInfo(EntityExample.class);
    }

    @Test
    public void getTableName() throws Exception {
        Assert.assertEquals("entity_example",entityExampleDataSetInfo.getTableName());
    }

    @Test
    public void getIdColumnName() throws Exception {
        Assert.assertEquals("id",entityExampleDataSetInfo.getIdColumnName());
    }

    @Test
    public void getIdField() throws Exception {
        Assert.assertEquals("id",entityExampleDataSetInfo.getIdField().getName());
    }

    @Test
    public void getColumnNameFieldMap() throws Exception {
        Map<String,String> expectedColumnNameFieldMap = new HashMap<>();
        expectedColumnNameFieldMap.put("id","id");
        expectedColumnNameFieldMap.put("name","name");
        expectedColumnNameFieldMap.put("job_title","jobTitle");

        Map<String,String> columnNameFieldMap = new HashMap<>();
        for(Map.Entry<String,Field> entry: entityExampleDataSetInfo.getColumnNameFieldMap().entrySet()){
            columnNameFieldMap.put(entry.getKey(),entry.getValue().getName());
        }

        Assert.assertEquals(expectedColumnNameFieldMap,columnNameFieldMap);
    }

    @Test
    public void getColumns() throws Exception {
        String[] expectedColumns = new String[]{"id","name","job_title"};
        Arrays.sort(expectedColumns);

        String[] columns = entityExampleDataSetInfo.getColumns().toArray(new String[0]);
        Arrays.sort(columns);

        Assert.assertArrayEquals(expectedColumns,columns);
    }

    @Test
    public void getFieldByColumnName() throws Exception {
        Assert.assertEquals("id",entityExampleDataSetInfo.getFieldByColumnName("id").getName());
        Assert.assertEquals("jobTitle",entityExampleDataSetInfo.getFieldByColumnName("job_title").getName());
        Assert.assertNull(entityExampleDataSetInfo.getFieldByColumnName("not_existed_column"));
    }

    @Test
    public void readIdValue() throws Exception {
        Assert.assertEquals(1L,entityExampleDataSetInfo.readIdValue(entityExample));
    }

    @Test
    public void readValueByColumnName() throws Exception {
        Assert.assertEquals(1L,entityExampleDataSetInfo.readValueByColumnName(entityExample,"id"));
        Assert.assertEquals("Some Name",entityExampleDataSetInfo.readValueByColumnName(entityExample,"name"));
        Assert.assertEquals("Job Title",entityExampleDataSetInfo.readValueByColumnName(entityExample,"job_title"));
        Assert.assertNull(entityExampleDataSetInfo.readValueByColumnName(entityExample,"not_existed_column"));
    }

    @Test
    public void readValues() throws Exception {
        Map<String,Object> expectedColumnValues = new HashMap<>();
        expectedColumnValues.put("id",1L);
        expectedColumnValues.put("name","Some Name");
        expectedColumnValues.put("job_title","Job Title");

        Map<String,Object> columnValues = new HashMap<>();
        for(Map.Entry<String,Object> entry: entityExampleDataSetInfo.readValues(entityExample).entrySet()){
            columnValues.put(entry.getKey(),entry.getValue());
        }

        Assert.assertEquals(expectedColumnValues,columnValues);
    }

}