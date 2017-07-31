package ru.otus.l81.jsonserializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Вспомогательные функции для работы с Java Reflection
 */
public class ReflectionHelper {

    /**
     * Возвращает перечень всех (включая public, protected, package access и private) полей объекта
     * включая поля поля унаследованные от всех родителей
     * @param object Объект для получения списка его полей
     * @return Перечень полей объекта
     */
    public static Iterable<Field> getAllFields(Object object){
        ArrayList<Field> fields = new ArrayList<>();

        Class clazz = object.getClass();

        while(clazz != null){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    /**
     * Возвращает значение поля объекта, включая значения private полей
     * @param object Объект с которого необходимо считать значения
     * @param field Поле которое нужно считать
     * @return Значение поля
     */
    public static Object getFieldValue(Object object, Field field) {
        boolean isAccessible = true;
        try {
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }
}
