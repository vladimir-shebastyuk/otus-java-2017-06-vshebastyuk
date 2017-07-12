package ru.orus.l51.testframework;

import ru.otus.l51.testframework.annotations.*;

/**
 * Created by jstingo on 11.07.2017.
 */
public class TestClassNoTests {
    @BeforeClass
    public static void beforeClass() throws Exception {
        throw new Exception("Should not be executed!");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        throw new Exception("Should not be executed!");
    }

    @Before
    public void before() throws Exception {
        throw new Exception("Should not be executed!");
    }

    @After
    public void after() throws Exception {
        throw new Exception("Should not be executed!");
    }

}
