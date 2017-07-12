package ru.orus.l51.testframework;

import ru.otus.l51.testframework.annotations.*;

/**
 *
 */
public class TestClassNonStaticBeforeAfterClass {
    @BeforeClass
    public void beforeClass(){
        System.out.println("BeforeClass " + this.getClass().getName());
    }

    @AfterClass
    public void afterClass(){
        System.out.println("AfterClass " + this.getClass().getName());
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
