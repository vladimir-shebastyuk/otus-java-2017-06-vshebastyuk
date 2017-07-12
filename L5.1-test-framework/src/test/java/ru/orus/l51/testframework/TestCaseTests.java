package ru.orus.l51.testframework;


import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import ru.otus.l51.testframework.TestCase;

/**
 * Тесты для класса
 */
public class TestCaseTests {


    @Test
    public void successfulTest() throws NoSuchMethodException {
        TestCase testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("successfulTest"),
                null,
                null
        );

        Assert.assertEquals(TestCase.ExecutionStatus.NOT_EXECUTED,testCase.getExecutionStatus());

        testCase.run();

        Assert.assertEquals(TestCase.ExecutionStatus.SUCCEEDED,testCase.getExecutionStatus());
    }

    @Test
    public void failedTest() throws NoSuchMethodException {
        TestCase testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("failedTest"),
                null,
                null
        );

        Assert.assertEquals(TestCase.ExecutionStatus.NOT_EXECUTED,testCase.getExecutionStatus());

        testCase.run();

        Assert.assertEquals(TestCase.ExecutionStatus.FAILED,testCase.getExecutionStatus());
    }

    @Test
    public void failedBeforeTest() throws NoSuchMethodException {
        TestCase testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("successfulTest"),
                TestClass.class.getMethod("failedBefore"),
                TestClass.class.getMethod("after")
        );

        Assert.assertEquals(TestCase.ExecutionStatus.NOT_EXECUTED,testCase.getExecutionStatus());

        testCase.run();

        Assert.assertEquals(TestCase.ExecutionStatus.FAILED,testCase.getExecutionStatus());
    }

    @Test
    public void failedAfterTest() throws NoSuchMethodException {
        TestCase testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("successfulTest"),
                TestClass.class.getMethod("before"),
                TestClass.class.getMethod("failedAfter")
        );

        Assert.assertEquals(TestCase.ExecutionStatus.NOT_EXECUTED,testCase.getExecutionStatus());

        testCase.run();

        Assert.assertEquals(TestCase.ExecutionStatus.SUCCEEDED,testCase.getExecutionStatus());
    }

    @Test
    public void elapsedTime() throws NoSuchMethodException {
        //тест без задержек
        TestCase testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("successfulTest"),
                null,
                null
        );

        testCase.run();

        Assert.assertTrue(testCase.getExecutionTimeMilliseconds() < 1000L);

        //тест с секундной задержкой
        testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("oneSecondTest"),
                null,
                null
        );

        testCase.run();

        Assert.assertTrue(testCase.getExecutionTimeMilliseconds() >= 1000L);
    }

    @Test
    public void executionOrder() throws NoSuchMethodException {
        TestClass mockTestClass = Mockito.mock(TestClass.class);

        TestCase testCase = new TestCase(
                TestClass.class,
                TestClass.class.getMethod("successfulTest"),
                TestClass.class.getMethod("before"),
                TestClass.class.getMethod("after")
        );

        TestCase spyTestCase = Mockito.spy(testCase);
        Mockito.doReturn(mockTestClass).when(spyTestCase).getTestClassObject();

        spyTestCase.run();

        InOrder inOrder = Mockito.inOrder(mockTestClass);

        inOrder.verify(mockTestClass).before();
        inOrder.verify(mockTestClass).successfulTest();
        inOrder.verify(mockTestClass).after();
    }
}
