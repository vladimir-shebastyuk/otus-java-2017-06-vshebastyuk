package ru.orus.l51.testframework;

import org.junit.Assert;
import org.junit.Test;
import ru.orus.l51.testframework.packagewithtests.TestClassA;
import ru.orus.l51.testframework.packagewithtests.TestClassB;
import ru.orus.l51.testframework.packagewithtests.TestClassC;
import ru.otus.l51.testframework.TestCaseClass;
import ru.otus.l51.testframework.TestCaseScanner;

import java.io.IOException;

/**
 */
public class TestCaseScannerTests {

    @Test
    public void getTestCaseClassesByNamesTest(){
        Iterable<TestCaseClass> testCaseClasses =
                TestCaseScanner.getTestCaseInClassesList(
                        "ru.orus.l51.testframework.packagewithtests.TestClassA",
                        "ru.orus.l51.testframework.packagewithtests.TestClassB",
                        "ru.orus.l51.testframework.packagewithtests.TestClassC"
                );

        testClassListFromTestPackage(testCaseClasses);
    }

    @Test
    public void getTestCaseClassesByClassesTest(){
        Iterable<TestCaseClass> testCaseClasses =
                TestCaseScanner.getTestCaseInClassesList(
                        ru.orus.l51.testframework.packagewithtests.TestClassA.class,
                        ru.orus.l51.testframework.packagewithtests.TestClassB.class,
                        ru.orus.l51.testframework.packagewithtests.TestClassC.class
                );

        testClassListFromTestPackage(testCaseClasses);
    }

    @Test
    public void getTestCaseClassesInPackageTest() throws IOException {
        Iterable<TestCaseClass> testCaseClasses =
                TestCaseScanner.getTestCaseInPackages("ru.orus.l51.testframework.packagewithtests");

        testClassListFromTestPackage(testCaseClasses);
    }

    /**
     * Проверяет, что список классов с тестами соответствует спику из пакета ru.orus.l51.testframework.packagewithtests
     * @param testCaseClasses
     */
    private void testClassListFromTestPackage(Iterable<TestCaseClass> testCaseClasses){
        boolean foundTestClassA = false;
        boolean foundTestClassB = false;
        boolean foundTestClassC = false;

        for(TestCaseClass testCaseClass: testCaseClasses){
            Class clazz = testCaseClass.getTestClass();
            if(clazz.equals(TestClassA.class)){
                foundTestClassA =  true;
            }else if(clazz.equals(TestClassB.class)){
                foundTestClassB =  true;
            }else if(clazz.equals(TestClassC.class)){
                foundTestClassC =  true;
            }else {
                Assert.fail("Class " + clazz.getName() + " out of list was found by scanner");
            }
        }

        Assert.assertTrue("Not all classes from list was found",foundTestClassA && foundTestClassB && foundTestClassC);
    }
}
