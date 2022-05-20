package spending;

import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.Payment;
import spending.model.UnusualPayment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.asList;

class DetectorTest {
    public static final String NORMAL_MONTH = "This month was normal";
    public static final String UNUSUAL_MONTH = "This month was normal";

    private final Detector detector = new Detector();

    @Test
    void returnsEmptySetWhenNoUnusualPaymentsAreDetected() {
        Set<Payment> thisMonthsPayments = initSet(new Payment(60, NORMAL_MONTH, Category.GROCERIES));
        Set<Payment> lastMonthsPayments = initSet(new Payment(50, NORMAL_MONTH, Category.GROCERIES));

        Set<UnusualPayment> actualUnusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);

        assertThat(actualUnusualPayments).isEmpty();
    }

    @Test
    void returnsOneCategoryWhenUnusualSpendingIsDetected() {
        Set<Payment> thisMonthsPayments = initSet(new Payment(200, UNUSUAL_MONTH, Category.GROCERIES));
        Set<Payment> lastMonthsPayments = initSet(new Payment(50, NORMAL_MONTH, Category.GROCERIES));

        Set<UnusualPayment> actualUnusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);

        assertThat(actualUnusualPayments).isEqualTo(
                new HashSet<>(
                        singletonList(new UnusualPayment(Category.GROCERIES, 200))
                ));
    }

    @Test
    void returnsOneCategoryWithMultiplePaymentsWhenUnusualSpendingIsDetected() {
        Set<Payment> thisMonthsPayments = initSet(
                new Payment(50, UNUSUAL_MONTH, Category.GROCERIES),
                new Payment(25, UNUSUAL_MONTH, Category.GROCERIES)
        );
        Set<Payment> lastMonthsPayments = initSet(new Payment(50, NORMAL_MONTH, Category.GROCERIES));

        Set<UnusualPayment> actualUnusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);

        assertThat(actualUnusualPayments).isEqualTo(
                new HashSet<>(
                        singletonList(new UnusualPayment(Category.GROCERIES, 75))
                ));
    }

    @Test
    void returnsMultipleCategoriesWhenUnusualSpendingIsDetected() {
        Set<Payment> thisMonthsPayments = initSet(
                new Payment(100, UNUSUAL_MONTH, Category.GROCERIES),
                new Payment(300, UNUSUAL_MONTH, Category.ENTERTAINMENT)
        );
        Set<Payment> lastMonthsPayments = initSet(
                new Payment(50, NORMAL_MONTH, Category.GROCERIES),
                new Payment(200, NORMAL_MONTH, Category.ENTERTAINMENT)
        );

        Set<UnusualPayment> actualUnusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);

        assertThat(actualUnusualPayments).isEqualTo(
                new HashSet<>(
                        asList(
                                new UnusualPayment(Category.GROCERIES, 100),
                                new UnusualPayment(Category.ENTERTAINMENT, 300)
                        )
                ));
    }

    private HashSet<Payment> initSet(Payment... payments) {
        return new HashSet<>(asList(payments));
    }
}