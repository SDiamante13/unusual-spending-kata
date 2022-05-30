package spending;

import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.UnusualPayment;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class SpendingCalculatorTest {

    private final SpendingCalculator spendingCalculator = new SpendingCalculator();

    @Test
    void calculatesTotalAmountOfUnusualSpendingWhenEmptySet() {
        Set<UnusualPayment> unusualPayments = Collections.emptySet();

        int actualTotalAmount = spendingCalculator.calculateTotalAmountOfUnusualSpending(unusualPayments);

        assertThat(actualTotalAmount).isZero();
    }

    @Test
    void calculatesTotalAmountOfUnusualSpendingWhenOneUnusualPayment() {
        Set<UnusualPayment> unusualPayments = new HashSet<>(singletonList(
                new UnusualPayment(Category.GROCERIES, 500)
        ));

        int actualTotalAmount = spendingCalculator.calculateTotalAmountOfUnusualSpending(unusualPayments);

        assertThat(actualTotalAmount).isEqualTo(500);
    }

    @Test
    void calculatesTotalAmountOfUnusualSpendingWhenMultipleUnusualPayments() {
        Set<UnusualPayment> unusualPayments = new HashSet<>(Arrays.asList(
                new UnusualPayment(Category.GROCERIES, 500),
                new UnusualPayment(Category.ENTERTAINMENT, 500)
        ));

        int actualTotalAmount = spendingCalculator.calculateTotalAmountOfUnusualSpending(unusualPayments);

        assertThat(actualTotalAmount).isEqualTo(1000);
    }
}
