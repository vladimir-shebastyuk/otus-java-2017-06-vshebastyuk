package ru.orus.l51.testframework;


import org.junit.Assert;
import ru.otus.l51.testframework.annotations.*;

/**
 * Специальный класс проверяющий, что методы запускаются у нужном порядке
 */
public class TestClassExecutionOrder {
    public static boolean beforeClassWasCalled;
    public static boolean afterClassWasCalled;
    private boolean beforeWasCalled;
    private boolean afterWasCalled;

    @BeforeClass
    public static void beforeClass(){
        Assert.assertFalse(beforeClassWasCalled);
        Assert.assertFalse(afterClassWasCalled);
        beforeClassWasCalled = true;
    }

    @AfterClass
    public static void afterClass(){
        Assert.assertTrue(beforeClassWasCalled);
        Assert.assertFalse(afterClassWasCalled);
        afterClassWasCalled = true;
    }

    @Before
    public void before(){
        Assert.assertTrue(beforeClassWasCalled);
        Assert.assertFalse(afterClassWasCalled);

        Assert.assertFalse(beforeWasCalled);
        Assert.assertFalse(afterWasCalled);

        beforeWasCalled = true;
    }

    @After
    public void after(){
        Assert.assertTrue(beforeClassWasCalled);
        Assert.assertFalse(afterClassWasCalled);

        Assert.assertTrue(beforeWasCalled);
        Assert.assertFalse(afterWasCalled);

        afterWasCalled = true;
    }

    @Test
    public void testOne(){
        Assert.assertTrue(beforeClassWasCalled);
        Assert.assertFalse(afterClassWasCalled);

        Assert.assertTrue(beforeWasCalled);
        Assert.assertFalse(afterWasCalled);
    }

    @Test
    public void testTwo(){
        Assert.assertTrue(beforeClassWasCalled);
        Assert.assertFalse(afterClassWasCalled);

        Assert.assertTrue(beforeWasCalled);
        Assert.assertFalse(afterWasCalled);
    }
}
