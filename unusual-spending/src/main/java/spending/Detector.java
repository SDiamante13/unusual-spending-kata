package spending;

import spending.model.Category;
import spending.model.Payment;
import spending.model.UnusualPayment;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Detector {

    public static final double ONE_HUNDRED_FIFTY_PERCENT = 1.5;

    public Set<UnusualPayment> detectUnusualPayments(Set<Payment> thisMonthsPayments, Set<Payment> lastMonthsPayments) {
        int thisMonthPriceForGroceries = getTotalPriceByCategory(thisMonthsPayments);

        int lastMonthPriceForGroceries = getTotalPriceByCategory(lastMonthsPayments);

        boolean isFiftyPercentMore = thisMonthPriceForGroceries >= lastMonthPriceForGroceries * ONE_HUNDRED_FIFTY_PERCENT;
        if (isFiftyPercentMore) {
            return new HashSet<>(Collections.singletonList(
                    new UnusualPayment(Category.GROCERIES, thisMonthPriceForGroceries)
            ));
        }
        return Collections.emptySet();
    }

    private int getTotalPriceByCategory(Set<Payment> payments) {
        return payments.stream()
                .map(Payment::price)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
