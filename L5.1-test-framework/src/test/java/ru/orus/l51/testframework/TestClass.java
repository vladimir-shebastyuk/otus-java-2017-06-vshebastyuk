package ru.orus.l51.testframework;

import ru.otus.l51.testframework.Assert;
import ru.otus.l51.testframework.annotations.After;
import ru.otus.l51.testframework.annotations.Before;
import ru.otus.l51.testframework.annotations.Test;

/**
 * Класс для тестирования TestCase
 */
public class TestClass {
    public void before(){}
    public void failedBefore() throws Exception {throw new Exception("Before execution exception");}
    public void after(){}
    public void failedAfter() throws Exception {throw new Exception("After execution exception");}
    public void successfulTest(){}
    public void failedTest(){
        Assert.assertEquals(1,2);
    }

    public void oneSecondTest() throws InterruptedException {Thread.sleep(1000);}

}
