package ru.orus.l51.testframework.packagewithtests;

import ru.otus.l51.testframework.annotations.*;

/**
 *
 */
public class TestClassA {
    @BeforeClass
    public static void beforeClass(){
        System.out.println("BeforeClass " + TestClassA.class.getName());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("AfterClass " + TestClassA.class.getName());
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
