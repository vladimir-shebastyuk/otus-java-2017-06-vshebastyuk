package ru.otus.l51.testframework;

import ru.otus.l51.testframework.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Тестовый кейс обрабатывающий одну функцию с аннотацией {@link Test @Test}
 */
public class TestCase {
    public enum ExecutionStatus {
        NOT_EXECUTED,
        SUCCEEDED,
        FAILED
    }

    private Method beforeMethod;
    private Method afterMethod;
    private Method testMethod;
    private Class testClass;
    private Object testClassObject;

    private ExecutionStatus executionStatus = ExecutionStatus.NOT_EXECUTED;
    private long executionTime = 0L;

    /**
     * @param testClass Класс с тестами
     * @param testMethod Метод тест-кейса
     * @param beforeMethod Метод вызваемый до запуска тест-кейса
     * @param afterMethod Метод вызываем после тест-кейса
     */
    public TestCase(Class testClass, Method testMethod, Method beforeMethod, Method afterMethod) {
        this.testMethod = testMethod;
        this.beforeMethod = beforeMethod;
        this.afterMethod = afterMethod;
        this.testClass = testClass;
    }

    private void executeBefore() throws Throwable {
        if(this.beforeMethod != null) {
            this.invokeMethod(this.beforeMethod);
        }
    }

    private void executeAfter() throws Throwable {
        if(this.afterMethod != null) {
            this.invokeMethod(this.afterMethod);
        }
    }

    /**
     * Запускает тест-кейс с учетом методом помеченных {@link ru.otus.l51.testframework.annotations.Before @Before}
     * и {@link ru.otus.l51.testframework.annotations.After @After}
     */
    public void run(){
        try {
            this.instantiateClassObject();

            this.executeBefore();

            long start = this.getCurrentTime();

            this.invokeMethod(this.testMethod);

            this.executionTime = this.getCurrentTime() - start;

            this.executionStatus = ExecutionStatus.SUCCEEDED;
        }catch(Throwable ex){
            ex.printStackTrace();
            this.executionStatus = ExecutionStatus.FAILED;
        }finally {
            try {
                this.executeAfter();
            }catch (Throwable ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * @return текущее время в миллисекундах
     */
    long getCurrentTime(){
        return new Date().getTime();
    }

    /**
     * @return Статус тест кейса
     */
    public ExecutionStatus getExecutionStatus(){
        return this.executionStatus;
    }

    /**
     * @return Время исполнения теста в миллисекундах
     */
    public long getExecutionTimeMilliseconds(){
        return this.executionTime;
    }

    private void invokeMethod(Method method) throws Throwable {
        try {
            method.invoke(getTestClassObject(), new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    public Object getTestClassObject() {
        return this.testClassObject;
    }

    void instantiateClassObject() throws IllegalAccessException, InstantiationException {
        this.testClassObject = this.testClass.newInstance();
    }
}
