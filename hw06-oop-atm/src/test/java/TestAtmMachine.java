import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.otus.ATMMachine;
import ru.otus.ATMMachineImpl;
import ru.otus.Banknote;
import ru.otus.Nominal;

/**
 *
 *
 * @author aozhigov
 * @since 9/23/22
 */
public class TestAtmMachine {
    private ATMMachine atmMachine;

    @Test
    void cashIn() {
        atmMachine.cashIn(List.of(new Banknote(Nominal.of(2000)),
                        new Banknote(Nominal.of(1000)),
                        new Banknote(Nominal.of(2000)),
                        new Banknote(Nominal.of(5000))));
        assert atmMachine.getBalance() == 16150;
    }

    @Test
    void cashOut() throws Exception {
        List<Banknote> banknotes = atmMachine.cashOut(1050);
        assert banknotes.size() == 2;
        assert atmMachine.getBalance() == 5100;
    }

    @Test
    void notEnoughMoney() {
        assertThatThrownBy(() -> atmMachine.cashOut(100500))
                .isInstanceOf(Exception.class)
                .hasMessage("Недостаточно средств");
    }

    @BeforeEach
    void setUp() {
        atmMachine = new ATMMachineImpl(
                List.of(
                        new Banknote(Nominal.of(1000)),
                        new Banknote(Nominal.of(50)),
                        new Banknote(Nominal.of(5000)),
                        new Banknote(Nominal.of(100))));
    }
}