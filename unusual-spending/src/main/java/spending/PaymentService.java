package spending;

import spending.model.Payment;

import java.util.Set;

public class PaymentService {
    private final FetchesUserPaymentsByMonth<Payment> fetchesUserPaymentsByMonth;

    public PaymentService() {
        this.fetchesUserPaymentsByMonth = FetchesUserPaymentsByMonth.getInstance();
    }

    public Set<Payment> fetchPayments(long userId, int year, int month) {
        return fetchesUserPaymentsByMonth.fetch(userId, year, month);
    }
}
