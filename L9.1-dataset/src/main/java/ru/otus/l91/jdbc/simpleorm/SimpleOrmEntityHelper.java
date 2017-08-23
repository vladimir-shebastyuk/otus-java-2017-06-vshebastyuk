package ru.otus.l91.jdbc.simpleorm;

import ru.otus.l91.datasets.DataSet;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 */
public class SimpleOrmEntityHelper {
    public static boolean isMappedSuperclass(Object object){
        return (object.getClass().getAnnotation(MappedSuperclass.class) != null);
    }

    /**
     * Проверяет корректен ли объект как сущность:
     *  - аннотировано ли он аннотацией @Entity
     *  - содержит ли конструктор с нулевым кол-вом аргументов
     *  - содержит поле id
     * @param clazz
     * @return
     */
    public static boolean isValidEntity(Class clazz){
        //проверяем наличие аннотации @Entity
        if (clazz.getAnnotation(Entity.class) == null) return false;

        //проверяем наличие конструктора без параметров
        if (!Stream.of(clazz.getConstructors())
                    .anyMatch((c) -> c.getParameterCount() == 0)) return false;

        //проверем наличие поля id
        if(getIdField(clazz) == null) return false;

        return true;
    }

    /**
     * Возвращает имя таблицы сущности
     * @param clazz
     * @return
     */
    public static String getTableName(Class clazz){
        String tableName = "";

        Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);

        if(tableAnnotation != null){
            tableName = tableAnnotation.name();
        }

        if (tableName.equals("")) {
            tableName = camelCaseToSnakeCase(clazz.getSimpleName());
        }

        return tableName;
    }

    /**
     * Возвращает имя колонки в БД для указанного поля
     * @param field
     * @return
     */
    public static String getColumnName(Field field){
        String columnName = "";

        Column columnAnnotation = field.getAnnotation(Column.class);

        if(columnAnnotation != null){
            columnName = columnAnnotation.name();
        }

        if(columnName.equals("")){
            columnName = camelCaseToSnakeCase(field.getName());
        }

        return columnName;
    }

    /**
     * Преобразует имена вида someFieldName в some_field_name
     * @param camelCaseName
     * @return
     */
    public static String camelCaseToSnakeCase(String camelCaseName){
        return camelCaseName.replaceAll("([a-zA-Z])([A-Z])([a-z])", "$1_$2$3").toLowerCase();
    }

    /**
     * Возвращает список сохраняемых полей для сущности
     * @param clazz Класс сущности
     * @return Список полей
     */
    public static Collection<Field> getPersistableFields(Class clazz){
        Collection<Field> persistableFields = new HashSet<>();

        for(Field field: ReflectionHelper.getAllFields(clazz)){
            if(!isTransient(field)) {
                persistableFields.add(field);
            }
        }

        return persistableFields;
    }

    /**
     * Возвращает список колонок/полей для сущности
     * @param clazz Класс сущности
     * @return Список колнонок/полей
     */
    private static Collection<String> getColumns(Class clazz){
        Collection<String> columns = new HashSet<>();

        for(Field field: ReflectionHelper.getAllFields(clazz)){
            if(!isTransient(field)) {
                String columnName = getColumnName(field);
                columns.add(columnName);
            }
        }

        return columns;
    }

    /**
     * Возвращает карту имяКолонки-значение из сущности
     * @param object Сущность
     * @return
     */
    public static Map<String,Object> getColumnValues(Object object){
        Map<String,Object> values = new HashMap<>();

        for(Field field: ReflectionHelper.getAllFields(object.getClass())){
            if(!isTransient(field)) {
                String columnName = getColumnName(field);
                Object value = ReflectionHelper.getFieldValue(object,field);
                values.put(columnName,value);
            }
        }

        return values;
    }

    /**
     * Проверяет следует ли исключить данное поле из сохранения. Поле исключается из
     * маппинга в случае если оно помечено либо модификатором transient либо аннотацией
     * <code>@Transient</code>
     * @param field
     * @return true если поле нужно проигнорировать (оно является transient)
     */
    public static boolean isTransient(Field field){
        if(Modifier.isTransient(field.getModifiers())||Modifier.isStatic(field.getModifiers())){
            return true;
        }

        return field.getAnnotation(Transient.class) != null;
    }

    public static boolean isId(Field field){
        return field.getDeclaredAnnotation(Id.class) != null;
    }

    /**
     * Проверяет что объект с незаданным id, т.е. вновь созданный объект, которые требует
     * сгенерировать id
     * @param object
     * @return
     * @throws SimpleOrmException
     */
    public static boolean isIdNotSet(Object object) throws SimpleOrmException {
        Field idField = getIdField(object.getClass());

        if(idField == null){
             throw new SimpleOrmException("There is should be field with @Id annotation");
        }

        long id = (long)ReflectionHelper.getFieldValue(object,idField);

        return isIdNotSet(id);
    }

    /**
     * Проверяет является ли данный id незаданным, т.е. как у вновь созданного объектаы
     * @param id
     * @return
     */
    public static boolean isIdNotSet(long id){
        return DataSet.NOT_SET_ID == id;
    }

    /**
     * Возвращает полей с id  в классе сущности
     * @param clazz
     * @return
     */
    public static Field getIdField(Class clazz){
        for(Field field: ReflectionHelper.getAllFields(clazz)){
            if(isId(field)){
                return field;
            }
        }

        return null;
    }

    /**
     * Возвращает имя id колонки
     * @param clazz
     * @return
     */
    public static String getIdColumn(Class clazz){
        return getColumnName(getIdField(clazz));
    }
}
