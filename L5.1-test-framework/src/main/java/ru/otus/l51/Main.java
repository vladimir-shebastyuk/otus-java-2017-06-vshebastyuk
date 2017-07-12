package ru.otus.l51;


import ru.otus.l51.testframework.TestFramework;

/**

 */
public class Main {
    public static void main(String... args) {
        TestFramework.runTestsInClassList(ru.otus.l51.TestClassSampleSingleTest.class);

        TestFramework.runTestsInClassList("ru.otus.l51.TestClassSampleSingleTest");

        TestFramework.runTestsInPackages("ru.otus.l51.sampleTestsPackage");
    }
}
