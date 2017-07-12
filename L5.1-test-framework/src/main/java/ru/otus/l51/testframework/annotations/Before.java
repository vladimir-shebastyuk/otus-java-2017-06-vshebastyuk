package ru.otus.l51.testframework.annotations;

import java.lang.annotation.*;

/**
 * Метод помеченный данной аннотацией будет вызван перед каждым вызова метода-тест-кейса
 * (метода отмеченного аннотацией {@link Test @Test})
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
}
