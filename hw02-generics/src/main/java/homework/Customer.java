package homework;

import java.util.Objects;

public class Customer {
    private final long id;
    private String name;
    private long scores;

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return id == ((Customer)o).id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getScores() {
        return scores;
    }

    @Override
    public int hashCode() {
        return (int)id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }
}
