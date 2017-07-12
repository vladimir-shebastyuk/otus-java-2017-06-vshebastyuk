package ru.orus.l51.testframework;

import ru.otus.l51.testframework.annotations.*;

/**
 *
 */
public class TestClassSuccessful {
    @BeforeClass
    public static void beforeClass(){
        System.out.println("BeforeClass " + TestClassSuccessful.class.getName());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("AfterClass " + TestClassSuccessful.class.getName());
    }

    @Before
    public void before(){
        System.out.println("Before " + this.getClass().getName());
    }

    @After
    public void after(){
        System.out.println("After " + this.getClass().getName());
    }

    @Test
    public void testOne(){
        System.out.println("TestOne " + this.getClass().getName());
    }

    @Test
    public void testTwo(){
        System.out.println("TestTwo " + this.getClass().getName());
    }
}
