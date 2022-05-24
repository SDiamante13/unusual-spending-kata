package spending;

import spending.model.Category;
import spending.model.Payment;
import spending.model.UnusualPayment;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class Detector {

    public static final double ONE_HUNDRED_FIFTY_PERCENT = 1.5;

    public Set<UnusualPayment> detectUnusualPayments(Set<Payment> thisMonthsPayments, Set<Payment> lastMonthsPayments) {
        Map<Category, Integer> thisMonthsCategoryToTotalPriceMap = thisMonthsPayments.stream()
                .collect(groupingBy(Payment::category, summingInt(Payment::price)));

        Map<Category, Integer> lastMonthsCategoryToTotalPriceMap = lastMonthsPayments.stream()
                .collect(groupingBy(Payment::category, summingInt(Payment::price)));

        return thisMonthsCategoryToTotalPriceMap.entrySet()
                .stream()
                .filter(thisMonthsEntry ->
                        isFiftyPercentMore(
                                thisMonthsEntry.getValue(),
                                lastMonthsCategoryToTotalPriceMap.get(thisMonthsEntry.getKey())
                        )
                )
                .map(entry -> new UnusualPayment(entry.getKey(), entry.getValue()))
                .collect(toSet());
    }

    private boolean isFiftyPercentMore(int thisMonthCategoryTotal, int lastMonthCategoryTotal) {
        return thisMonthCategoryTotal >= lastMonthCategoryTotal * ONE_HUNDRED_FIFTY_PERCENT;
    }
}
