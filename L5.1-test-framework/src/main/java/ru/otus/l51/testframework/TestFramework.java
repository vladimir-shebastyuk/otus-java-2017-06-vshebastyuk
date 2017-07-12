package ru.otus.l51.testframework;

import java.io.IOException;

/**
 * Основной класс тестового фреймворка для запуска тестов
 */
public class TestFramework {
    /**
     * Запускает тесты из классов из списка
     * @param classes
     */
    public static void runTestsInClassList(Class ...classes){
        Iterable<TestCaseClass> testCaseClasses =
                TestCaseScanner.getTestCaseInClassesList(classes);

        for (TestCaseClass testCaseClass: testCaseClasses){
            testCaseClass.run();
        }
    }

    /**
     * Запускает тесты из классов из списка имен классов
     * @param classNames
     */
    public static void runTestsInClassList(String ...classNames){
        Iterable<TestCaseClass> testCaseClasses =
                TestCaseScanner.getTestCaseInClassesList(classNames);

        for (TestCaseClass testCaseClass: testCaseClasses){
            testCaseClass.run();
        }
    }

    /**
     * Запускает тесты из пакетов из списка
     * @param packageNames
     */
    public static void runTestsInPackages(String ...packageNames){
        try {
            Iterable<TestCaseClass> testCaseClasses =
                    null;

            testCaseClasses = TestCaseScanner.getTestCaseInPackages(packageNames);

            for (TestCaseClass testCaseClass: testCaseClasses){
                testCaseClass.run();
            }
        } catch (IOException e) {
            System.out.println("Error on scanning package list");
        }
    }
}
