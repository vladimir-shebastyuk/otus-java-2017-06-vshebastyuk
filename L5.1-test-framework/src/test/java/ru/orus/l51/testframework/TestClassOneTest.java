package ru.orus.l51.testframework;

import ru.otus.l51.testframework.annotations.*;

/**
 * Created by jstingo on 11.07.2017.
 */
public class TestClassOneTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println("BeforeClass " + TestClassOneTest.class.getName());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("AfterClass " + TestClassOneTest.class.getName());
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
