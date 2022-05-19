package spending;

import spending.model.Category;
import spending.model.Payment;
import spending.model.UnusualPayment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Detector {
    public Set<UnusualPayment> detectUnusualPayments(Set<Payment> thisMonthsPayments, Set<Payment> lastMonthsPayments) {
        int thisMonthPriceForGroceries = thisMonthsPayments.stream()
                .map(Payment::price)
                .mapToInt(Integer::intValue)
                .sum();

        int lastMonthPriceForGroceries = lastMonthsPayments.stream()
                .map(Payment::price)
                .mapToInt(Integer::intValue)
                .sum();

        boolean isFiftyPercentMore = thisMonthPriceForGroceries > lastMonthPriceForGroceries * 1.5;
        if (isFiftyPercentMore) {
            return new HashSet<>(Collections.singletonList(
                    new UnusualPayment(Category.GROCERIES, thisMonthPriceForGroceries)
            ));
        }
        return Collections.emptySet();
    }
}
