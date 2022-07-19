package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService
{
    private final TreeMap<Customer, String> innerMap =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest()
    {
        return cloneMapEntry(innerMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer)
    {
        return cloneMapEntry(innerMap.higherEntry(customer));
    }

    public void add(Customer customer, String data)
    {
        innerMap.put(customer, data);
    }

    private static Map.Entry<Customer, String> cloneMapEntry(Map.Entry<Customer, String> entry)
    {
        if (entry == null)
            return null;
        Customer temp = entry.getKey();
        return Map.entry(new Customer(temp.getId(), temp.getName(), temp.getScores()),
                entry.getValue());
    }
}
