package ru.otus.l91.jdbc.simpleorm;

import ru.otus.l91.datasets.DataSet;

import java.sql.*;
import java.util.*;

/**
 *
 */
public class SimpleOrm<T extends DataSet> {
    private Executor executor;

    public SimpleOrm(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void save(DataSet dataSet) throws SimpleOrmException {
        DataSetInfo dataSetInfo = new DataSetInfo(dataSet.getClass());

        long id = dataSetInfo.readIdValue(dataSet);

        if(SimpleOrmEntityHelper.isIdNotSet(id)){//вставляется новая запись с генерацией ID
            Map<String,Object> columnValues = dataSetInfo.readValues(dataSet);

            //исключаем поле id, чтобы его значение сгенерировалось базой
            columnValues.remove(dataSetInfo.getIdColumnName());

            String sql = SimpleOrmSqlHelper.formatInsertSql(dataSetInfo.getTableName(),columnValues.keySet());
            try {
                id = this.executor.insertWithGeneratedId(sql, columnValues.values().toArray());

                dataSet.setId(id);
            }catch(SQLException ex){
                throw new SimpleOrmException("Error on saving object to DB",ex);
            }
        }else{//проверям есть элемент с таким id в базе

            String sql = SimpleOrmSqlHelper.formatSelectByIdSql(dataSetInfo.getTableName(),dataSetInfo.getIdColumnName());
            try {
                Map<String,Object> values = this.executor.selectSingleById(sql,id,Collections.singletonList(dataSetInfo.getIdColumnName()));

                if(values.size() == 0){//в базе такого элемента не найдено, поэтому сохраняем его полностью с id
                    Map<String,Object> columnValues = dataSetInfo.readValues(dataSet);

                    sql = SimpleOrmSqlHelper.formatInsertSql(dataSetInfo.getTableName(),columnValues.keySet());

                    this.executor.insert(sql, columnValues.values().toArray());
                }else{//в базе уже есть элемент с таким id - делаем update

                    Map<String,Object> columnValues = dataSetInfo.readValues(dataSet);

                    //исключаем поле id, чтобы его значение сгенерировалось базой
                    columnValues.remove(dataSetInfo.getIdColumnName());

                    sql = SimpleOrmSqlHelper.formatUpdateSql(dataSetInfo.getTableName(),dataSetInfo.getIdColumnName(),columnValues.keySet());

                    this.executor.update(sql,dataSetInfo.readIdValue(dataSet),columnValues.values().toArray());
                }

            }catch(SQLException ex){
                throw new SimpleOrmException("Error on saving object to DB",ex);
            }
        }
    }

    public T load(long id, Class<T> clazz) throws SimpleOrmException {
        DataSetInfo dataSetInfo = new DataSetInfo(clazz);

        String sql = SimpleOrmSqlHelper.formatSelectByIdSql(
                dataSetInfo.getTableName(),
                dataSetInfo.getIdColumnName(),
                dataSetInfo.getColumns()
        );

        try {
            DataSet dataSet = clazz.newInstance();

            for (Map.Entry<String, Object> columnValues : this.executor.selectSingleById(sql, id, dataSetInfo.getColumns()).entrySet()) {
                ReflectionHelper.setFieldValue(dataSet,dataSetInfo.getFieldByColumnName(columnValues.getKey()),columnValues.getValue());
            }

            //noinspection unchecked
            return (T)dataSet;
        }catch (SQLException ex){
            throw new SimpleOrmException("Could loadUser enity",ex);
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new SimpleOrmException("Could not instantiate new " + clazz.getName(),ex);
        }
    }
}
