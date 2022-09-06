import java.lang.reflect.InvocationTargetException;

import ru.otus.runner.TestRunner;

public class Main {
    public static void main(String[] args)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        TestRunner.checkMethodAnnotation(TempTest.class.getSimpleName());
        TestRunner.run();
    }
}
