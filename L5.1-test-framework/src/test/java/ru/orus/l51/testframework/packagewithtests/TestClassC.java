package ru.orus.l51.testframework.packagewithtests;

import ru.otus.l51.testframework.annotations.*;

/**
 *
 */
public class TestClassC {
    @BeforeClass
    public static void beforeClass(){
        System.out.println("BeforeClass " + TestClassC.class.getName());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("AfterClass " + TestClassC.class.getName());
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
}
