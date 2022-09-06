import org.junit.jupiter.api.Assertions;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TempTest {

    @Before
    void beforeEach() {
        System.out.println(this.getClass().getName() + " - Before");
    }

    @Test
    void test1() {
        System.out.println(this.getClass().getName() + " - Test 1");
        Assertions.assertEquals(2, 2);
    }

    @Test
    void test2() {
        System.out.println(this.getClass().getName() + " - Test 2");
        Assertions.assertEquals("one", "two");
    }

    @After
    void afterEach() {
        System.out.println(this.getClass().getName() + " - After");
    }
}