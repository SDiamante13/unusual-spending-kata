package spending.model;

import java.util.Objects;

public class UnusualPayment {
    private final Category category;
    private final int amount;

    public UnusualPayment(Category category, int amount) {
        this.category = category;
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }

    public Category category() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnusualPayment that = (UnusualPayment) o;
        return amount == that.amount && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, amount);
    }

    @Override
    public String toString() {
        return "UnusualPayment{" +
                "category=" + category +
                ", amount=" + amount +
                '}';
    }
}
