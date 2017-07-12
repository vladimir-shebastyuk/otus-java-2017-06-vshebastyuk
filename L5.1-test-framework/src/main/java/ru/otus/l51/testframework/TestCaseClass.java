package ru.otus.l51.testframework;

import ru.otus.l51.testframework.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Тестовый кейс обрабатывающий одну функцию с аннотацией {@link Test @Test}
 */
public class TestCaseClass {
    private Method beforeClassMethod;
    private Method afterClassMethod;

    private Class testClass;

    private List<TestCase> testCases = new ArrayList<>();

    private int successfulTests = 0;
    private int failedTests = 0;

    /**
     *
     * @param testClass Класс (тип) тестового класса, содержащего методы проаннотированые {@link Test @Test}
     */
    public TestCaseClass(Class testClass) {
        this.testClass = testClass;
        scanForTest(testClass);
    }

    /**
     * @return Класс (тип) содержащий тесты
     */
    public Class getTestClass() {
        return testClass;
    }

    /**
     * Кол-во успешно прошедших тестов
     * @return
     */
    public int getSuccessfulTests() {
        return successfulTests;
    }

    /**
     * Кол-во провалившихся тестов
     * @return
     */
    public int getFailedTests() {
        return failedTests;
    }

    void scanForTest(Class clazz){
        Method beforeMethod = null;
        Method afterMethod = null;

        List<Method> testMethods = new ArrayList<>();

        for(Method method: clazz.getDeclaredMethods()){
            Annotation testAnnotation = method.getAnnotation(Test.class);
            if(testAnnotation != null){
                testMethods.add(method);
            }

            Annotation beforeAnnotation = method.getAnnotation(Before.class);
            if(beforeAnnotation != null){
                beforeMethod = method;
            }

            Annotation afterAnnotation = method.getAnnotation(After.class);
            if(afterAnnotation != null){
                afterMethod = method;
            }

            Annotation beforeClassAnnotation = method.getAnnotation(BeforeClass.class);
            if(beforeClassAnnotation != null){
                this.beforeClassMethod = method;
            }

            Annotation afterClassAnnotation = method.getAnnotation(AfterClass.class);
            if(afterClassAnnotation != null){
                this.afterClassMethod = method;
            }
        }

        for (Method testMethod: testMethods){
            TestCase testCase = new TestCase(clazz,testMethod,beforeMethod,afterMethod);
            this.testCases.add(testCase);
        }
    }

    public void executeBeforeClass() throws Throwable {
        if(this.beforeClassMethod != null){
            this.invokeStaticMethod(this.beforeClassMethod);
        }
    }

    public void executeAfterClass() throws Throwable {
        if(this.afterClassMethod != null){
            this.invokeStaticMethod(this.afterClassMethod);
        }
    }

    /**
     * Запуск всех тестов тестового класса на исполнение
     */
    public void run(){
        if(!this.testCases.isEmpty()) {
            this.reportExecutionStart();
            this.successfulTests = 0;
            this.failedTests = 0;
            long totalTimeElapsed = 0;

            try {
                this.executeBeforeClass();

                for (TestCase testCase : this.testCases) {
                    testCase.run();

                    switch (testCase.getExecutionStatus()) {
                        case SUCCEEDED:
                            this.successfulTests++;
                            break;
                        case FAILED:
                            this.failedTests++;
                            break;
                    }

                    totalTimeElapsed += testCase.getExecutionTimeMilliseconds();
                }

                this.executeAfterClass();
            }catch (Throwable ex){
                ex.printStackTrace();
            }

            this.reportExecutionResults(this.successfulTests + this.failedTests ,this.successfulTests,this.failedTests,totalTimeElapsed);
        }
    }

    private void reportExecutionStart(){
        System.out.println(String.format("Run test for %s", this.testClass.getName()));
    }

    private void reportExecutionResults(int run, int succeeded, int failed, long timeElapsed){
        System.out.println(String.format("Tests run: %d, Succeeded: %d, Failed: %d, Time elapsed: %f sec",run,succeeded,failed,timeElapsed / 1000.0));
    }

    private void invokeStaticMethod(Method method) throws Throwable {
        try {
            if(Modifier.isStatic(method.getModifiers())) {
                method.invoke(null, new Object[0]);
            }else{
                throw new Error("Method " + method.getName() + " should be static!");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
           throw e.getCause();
        }
    }

}
