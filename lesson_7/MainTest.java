package lesson_7;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        start(TestClass.class);
        start(TestClass.class.getName());
    }

    private static void start(Class <?>testedClass) {
        try {
            runMethods(testedClass);
        } catch (RuntimeException e) {
            System.out.println("В тестовом классе больше одного метода с аннотацией after или before");
        }

    }

    private static void start(String className) {
        Class<?> testedClass = null;
        try {
            testedClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        start(testedClass);
    }

    private static void runMethods(@NotNull Class <?> testedClass) {
        Method[] methods = testedClass.getDeclaredMethods();
        List<Method> beforeSuiteMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterSuiteMethods = new ArrayList<>();
        for (Method currentMethod : methods) {
            if (currentMethod.getAnnotation(BeforeSuite.class) != null) beforeSuiteMethods.add(currentMethod);
            if (currentMethod.getAnnotation(Test.class) != null) testMethods.add(currentMethod);
            if (currentMethod.getAnnotation(AfterSuite.class) != null) afterSuiteMethods.add(currentMethod);
        }
        checkedBeforeAndAfterMethod(beforeSuiteMethods, afterSuiteMethods);


        try {
            TestClass testClass = (TestClass) testedClass.newInstance();
            beforeSuiteMethods.get(0).invoke(testClass);
            for (int i = 1; i <= 10; i++) {
                for (Method currentMethod : testMethods) {
                    if (currentMethod.getAnnotation(Test.class).priority() == i) {
                        currentMethod.invoke(testClass);
                    }
                }
            }
            afterSuiteMethods.get(0).invoke(testClass);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private static void checkedBeforeAndAfterMethod(List<Method> beforeSuiteMethods, List<Method> afterSuiteMethods) {
        if (beforeSuiteMethods.size() != 1 || afterSuiteMethods.size() != 1)
            throw new RuntimeException();
    }
}
