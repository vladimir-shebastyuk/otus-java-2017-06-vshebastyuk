package ru.otus.l51.testframework;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Возвращает перечень тест классов по заданным параметрам поиска
 */
public class TestCaseScanner {
    private TestCaseScanner(){}

    /**
     * Находит тест классы по списку имен классов
     * @param classNames
     * @return Список тест классов
     */
    public static Iterable<TestCaseClass> getTestCaseInClassesList(String... classNames){
        List<TestCaseClass> testCaseClasses = new ArrayList<>();

        for (String className: classNames){
            Class clazz = TestCaseScanner.getClassByName(className);
            if(clazz != null){
                testCaseClasses.add(new TestCaseClass(clazz));
            }
        }

        return testCaseClasses;
    }

    /**
     * Находит тест классы по списку классов
     * @param classes
     * @return Список тест классов
     */
    public static Iterable<TestCaseClass> getTestCaseInClassesList(Class... classes){
        List<TestCaseClass> testCaseClasses = new ArrayList<>();

        for (Class clazz: classes){
            if(clazz != null){
                testCaseClasses.add(new TestCaseClass(clazz));
            }
        }

        return testCaseClasses;
    }

    /**
     * Находит тест классы по списку имен пакетов
     * @param packageNames
     * @return Список пакетов
     */
    public static Iterable<TestCaseClass> getTestCaseInPackages(String... packageNames) throws IOException {
        List<TestCaseClass> testCaseClasses = new ArrayList<>();

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPath classPath = ClassPath.from(loader);

        for (String packageName: packageNames){
            for(final ClassPath.ClassInfo info: classPath.getTopLevelClasses(packageName)) {
                Class clazz = info.load();
                if (clazz != null) {
                    testCaseClasses.add(new TestCaseClass(clazz));
                }
            }
        }

        return testCaseClasses;
    }

    static Class getClassByName(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
           return null;
        }
    }
}
