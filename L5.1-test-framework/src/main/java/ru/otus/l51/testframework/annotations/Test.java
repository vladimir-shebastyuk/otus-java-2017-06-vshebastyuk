package ru.otus.l51.testframework.annotations;

import java.lang.annotation.*;

/**
 * Отмечает метод класса как тест-кейс
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
