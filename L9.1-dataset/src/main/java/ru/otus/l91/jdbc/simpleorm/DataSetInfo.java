package ru.otus.l91.jdbc.simpleorm;

import ru.otus.l91.datasets.DataSet;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс содержащий метаинформацию по сущности указанного класса
 */
public class DataSetInfo {
    private String tableName;
    private String idColumnName;
    private Field idField;

    private Map<String, Field> columnNameFieldMap;

    public DataSetInfo(Class clazz) throws SimpleOrmException {
        if(!SimpleOrmEntityHelper.isValidEntity(clazz)){
            throw new SimpleOrmException("Cannot saveUser not valid dataSet class!");
        }

        this.tableName = SimpleOrmEntityHelper.getTableName(clazz);
        this.idField = SimpleOrmEntityHelper.getIdField(clazz);
        this.idColumnName = SimpleOrmEntityHelper.getColumnName(this.idField);

        this.columnNameFieldMap = new LinkedHashMap<>();

        for(Field field: SimpleOrmEntityHelper.getPersistableFields(clazz)){
            this.columnNameFieldMap.put(
                    SimpleOrmEntityHelper.getColumnName(field),
                    field
            );
        }
    }

    public String getTableName() {
        return tableName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public Field getIdField() {
        return idField;
    }

    public Map<String, Field> getColumnNameFieldMap() {
        return columnNameFieldMap;
    }

    public Set<String> getColumns(){
        return this.columnNameFieldMap.keySet();
    }

    public Field getFieldByColumnName(String columnName){
        return this.columnNameFieldMap.get(columnName);
    }

    public long readIdValue(DataSet dataSet){
        return (long)ReflectionHelper.getFieldValue(dataSet,idField);
    }

    public Object readValueByColumnName(DataSet dataSet, String columnName){
        Field field = columnNameFieldMap.get(columnName);
        if(field !=  null) {
            return ReflectionHelper.getFieldValue(dataSet, field);
        }else{
            return null;
        }
    }

    public Map<String, Object> readValues(DataSet dataSet){
        Map<String, Object> result = new LinkedHashMap<>();

        for (Map.Entry<String,Field> entry: columnNameFieldMap.entrySet()){
            result.put(entry.getKey(), readValueByColumnName(dataSet,entry.getKey()));
        }

        return result;
    }
}
