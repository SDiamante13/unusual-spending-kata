package spending;

import spending.model.UnusualPayment;

import java.util.Set;

public class SpendingCalculator {

    public int calculateTotalAmountOfUnusualSpending(Set<UnusualPayment> unusualPayments) {
        if (unusualPayments.isEmpty()) {
            return 0;
        }

        return unusualPayments.stream()
                .mapToInt(UnusualPayment::amount)
                .sum();
    }
}
