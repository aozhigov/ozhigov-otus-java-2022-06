package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> innerDeque = new ArrayDeque<>();

    public void add(Customer customer) {
        innerDeque.push(customer);
    }

    public Customer take() {
        return innerDeque.pop();
    }
}
