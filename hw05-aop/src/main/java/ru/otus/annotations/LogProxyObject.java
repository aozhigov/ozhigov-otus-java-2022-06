package ru.otus.annotations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 *
 * @author aozhigov
 * @since 9/23/22
 */
public abstract class LogProxyObject {
    private record LogInvocationHandler<T>(T instance) implements InvocationHandler {

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            final String nameAndParams = extractNameAndParams(method);

            if (methodsForLogging.contains(nameAndParams)) {
                printMethodInfo(method, args);
            }

            return method.invoke(instance, args);
        }
    }

    private static final String METHOD_INFO_TEMPLATE_MSG = "executed method: %s, with params : %s";
    private static final Set<String> methodsForLogging = new HashSet<>();

    public static <T> T getInstance(final T obj) {
        final Class<?> clazz = obj.getClass();
        final Method[] methods = clazz.getMethods();
        final Set<Method> annotatedMethods = filterMethodsByAnnotation(methods);

        if (annotatedMethods.isEmpty()) {
            throw new IllegalArgumentException("Not found annotation: " + Log.class.getCanonicalName());
        }

        final Set<String> namesAndParams = annotatedMethods.stream()
                .map(LogProxyObject::extractNameAndParams)
                .collect(Collectors.toSet());

        methodsForLogging.addAll(namesAndParams);

        return (T)Proxy.newProxyInstance(LogProxyObject.class.getClassLoader(), clazz.getInterfaces(),
                new LogInvocationHandler<>(obj));
    }

    private static String extractNameAndParams(final Method method) {
        return method.getName() + Arrays.toString(method.getParameters());
    }

    private static Set<Method> filterMethodsByAnnotation(final Method[] methods) {
        final Predicate<Method> predicate = (method) -> method.isAnnotationPresent(Log.class);
        return Arrays.stream(methods).filter(predicate).collect(Collectors.toSet());
    }

    private static <T> void printMethodInfo(final Method method, final Object[] args) {
        final String methodName = method.getName();
        assert args.length == method.getParameterCount();
            System.out.printf((METHOD_INFO_TEMPLATE_MSG) + "%n",
                    methodName, Arrays.toString(method.getParameters()) + " = " + Arrays.toString(args));
    }
}