package spending.model;

import java.util.Objects;

public class Payment {
    private final int price;
    private final String description;
    private final Category category;

    public Payment(int price, String description, Category category) {
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public int price() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Float.compare(payment.price, price) == 0 && Objects.equals(description, payment.description) && category == payment.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, description, category);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "price=" + price +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }
}
