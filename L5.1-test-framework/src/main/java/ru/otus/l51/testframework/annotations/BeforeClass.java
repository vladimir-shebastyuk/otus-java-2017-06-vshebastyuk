package ru.otus.l51.testframework.annotations;

import java.lang.annotation.*;

/**
 * Метод помеченный данной аннотацией будет вызван один раз после запуска методов-тест-кейсов
 * (методов отмеченных аннотацией {@link Test @Test}) в классе.
 *
 * Метод должен быть статическим.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeClass {
}
