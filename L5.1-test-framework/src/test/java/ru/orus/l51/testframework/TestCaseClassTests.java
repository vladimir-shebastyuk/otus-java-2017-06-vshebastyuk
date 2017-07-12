package ru.orus.l51.testframework;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.l51.testframework.TestCaseClass;

/**
 */
public class TestCaseClassTests {

    /**
     * Проверяем успешный запуск всех тестов
     */
    @Test
    public void allSuccessful(){
        TestCaseClass testCaseClass = new TestCaseClass(TestClassSuccessful.class);

        Assert.assertEquals(0,testCaseClass.getSuccessfulTests());
        Assert.assertEquals(0,testCaseClass.getFailedTests());

        testCaseClass.run();

        Assert.assertEquals(2,testCaseClass.getSuccessfulTests());
        Assert.assertEquals(0,testCaseClass.getFailedTests());
    }

    /**
     * Проверяем ошибку запуска в одном из трех тестов
     */
    @Test
    public void oneOfThreeFail(){
        TestCaseClass testCaseClass = new TestCaseClass(TestClassTwoOfThree.class);

        Assert.assertEquals(0,testCaseClass.getSuccessfulTests());
        Assert.assertEquals(0,testCaseClass.getFailedTests());

        testCaseClass.run();

        Assert.assertEquals(2,testCaseClass.getSuccessfulTests());
        Assert.assertEquals(1,testCaseClass.getFailedTests());
    }

    /**
     * Проверяем не запуск before, after, beforeClass и afterClass методов
     */
    @Test
    public void noTests(){
        TestCaseClass testCaseClass = new TestCaseClass(TestClassNoTests.class);

        testCaseClass.run(); //не должен быть запущен ни один из before или after методов, т.к. к в классе нет ни одного теста
        Assert.assertEquals(0,testCaseClass.getSuccessfulTests());
        Assert.assertEquals(0,testCaseClass.getFailedTests());
    }

    @Test
    public void executionOrder(){
        TestCaseClass testCaseClass = new TestCaseClass(TestClassExecutionOrder.class);

        testCaseClass.run();

        Assert.assertEquals(2,testCaseClass.getSuccessfulTests());
        Assert.assertEquals(0,testCaseClass.getFailedTests());

        Assert.assertTrue(TestClassExecutionOrder.afterClassWasCalled);
    }

    /**
     * Проверяем не запуск тестов при нестатических beforeClass и afterClass методах
     */
    @Test
    public void nonStaticBeforeAfterClass(){
        TestCaseClass testCaseClass = new TestCaseClass(TestClassNonStaticBeforeAfterClass.class);

        testCaseClass.run();

        Assert.assertEquals(0,testCaseClass.getSuccessfulTests()); //ни один тест не должен быть запущен
        Assert.assertEquals(0,testCaseClass.getFailedTests());
    }

}
