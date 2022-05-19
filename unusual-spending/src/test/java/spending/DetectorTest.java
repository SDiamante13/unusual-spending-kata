package spending;

import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.Payment;
import spending.model.UnusualPayment;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class DetectorTest {
    public static final String NORMAL_MONTH = "This month was normal";

    private final Detector detector = new Detector();

    @Test
    void returnsEmptySetWhenNoUnusualPaymentsAreDetected() {
        Set<Payment> thisMonthsPayments = initSet(new Payment(60, NORMAL_MONTH, Category.GROCERIES));
        Set<Payment> lastMonthsPayments = initSet(new Payment(50, NORMAL_MONTH, Category.GROCERIES));

        Set<UnusualPayment> actualUnusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);

        assertThat(actualUnusualPayments).isEmpty();
    }

    @Test
    void returnsOneCategoryWhenUnusualPaymentsAreDetected() {
        Payment payment = new Payment(200, "Food for yet another month", Category.GROCERIES);
        Set<Payment> thisMonthsPayments = initSet(payment);
        Set<Payment> lastMonthsPayments = initSet(new Payment(50, "Food for month", Category.GROCERIES));

        Set<UnusualPayment> actualUnusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);

        assertThat(actualUnusualPayments).isEqualTo(
                new HashSet<>(
                        singletonList(new UnusualPayment(Category.GROCERIES, 200))
                ));
    }

    private HashSet<Payment> initSet(Payment payment) {
        return new HashSet<>(
                singletonList(payment)
        );
    }
}