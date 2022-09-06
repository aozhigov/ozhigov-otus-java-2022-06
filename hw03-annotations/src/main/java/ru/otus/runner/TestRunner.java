package ru.otus.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestRunner {
    private static final String TESTS_FINISHED = "Tests are finished!";

    private static final String TEST_RESULT = "executed: %s, passed: %s, failed: %s";

    private static void invokeMethod(Method method, Object instance)
            throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(instance);
    }

    private final Class<?> testClass;
    private final TestStatistics testsResult = new TestStatistics();
    private final Set<Method> beforeMethods = new HashSet<>();
    private final Set<Method> afterMethods = new HashSet<>();
    private final Set<Method> testMethods = new HashSet<>();

    public TestRunner(String className) throws ClassNotFoundException {
        testClass = Class.forName(className);
        Method[] methods = testClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getAnnotation(Before.class) != null) {
                beforeMethods.add(method);
            }
            else if (method.getAnnotation(Test.class) != null) {
                testMethods.add(method);
            }
            else if (method.getAnnotation(After.class) != null) {
                afterMethods.add(method);
            }
        }
    }

    public void run() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        boolean withoutError = true;
        for (Method testMethod : testMethods) {
            Object instance = testClass.getDeclaredConstructor().newInstance();
            try {
                if (invokeMethods(beforeMethods, instance)) {
                    invokeMethod(testMethod, instance);
                }
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                if (e instanceof InvocationTargetException) {
                    Throwable targetException = ((InvocationTargetException)e).getTargetException();
                    if (targetException instanceof AssertionError) {
                        System.err.println(targetException.getMessage());
                    }
                }
                testsResult.addError();
                withoutError = false;
            }
            finally {
                if (invokeMethods(afterMethods, instance) & withoutError) {
                    testsResult.addSuccess();
                }
            }
        }

        System.out.println(TESTS_FINISHED);
        System.out.printf((TEST_RESULT) + "%n", testMethods.size(), testsResult.getSuccesses(),
                testsResult.getErrors());
    }

    private static boolean invokeMethods(Set<Method> methods, Object instance) {
        boolean withoutError = true;
        try {
            for (Method afterMethod : methods) {
                invokeMethod(afterMethod, instance);
            }
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println(e.getMessage());
            withoutError = false;
        }

        return withoutError;
    }
}
