package spending;

import spending.model.Payment;
import spending.model.UnusualPayment;

import java.time.LocalDate;
import java.util.Set;

public class TriggersUnusualSpendingEmail {

    private final Moment moment;
    private final PaymentService paymentService;
    private final Detector detector;
    private final SpendingCalculator spendingCalculator;
    private final EmailFormatter emailFormatter;
    private final EmailService emailService;

    public TriggersUnusualSpendingEmail(Moment moment, PaymentService paymentService, Detector detector, SpendingCalculator spendingCalculator, EmailFormatter emailFormatter, EmailService emailService) {
        this.moment = moment;
        this.paymentService = paymentService;
        this.detector = detector;
        this.spendingCalculator = spendingCalculator;
        this.emailFormatter = emailFormatter;
        this.emailService = emailService;
    }

    public void trigger(long userId) {
        LocalDate now = moment.now();
        Set<Payment> thisMonthsPayments = paymentService.fetchPayments(userId, now.getYear(), now.getMonthValue());
        Set<Payment> lastMonthsPayments = paymentService.fetchPayments(userId, now.getYear(), now.minusMonths(1L).getMonthValue());
        Set<UnusualPayment> unusualPayments = detector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments);
        int totalAmountOfUnusualSpending = spendingCalculator.calculateTotalAmountOfUnusualSpending(unusualPayments);
        String emailBody = emailFormatter.formatBody(unusualPayments);
        emailService.publish(userId, "Unusual spending of $" + totalAmountOfUnusualSpending + " detected!", emailBody);
    }

}

// email : email(long userId, String subject, String body)
// fetchPayments : Set<T> fetch(long userId, int year, int month)

// fetch the payments for the current month and the previous month
// Compare the total amount paid for each month,
// grouped by category; filter down to the categories for which the user spent at least 50% more this month than last month
// detectUnusualPayments returns Set<UnusualPayment> which represents the categories and total amounts.
// need method to take Set<UnusualPayment> and construct email body - EmailFormatter.formatBody(Set<UnusualSpending>)
// Compose an e-mail message to the user that lists the categories for which spending was unusually high, with a
// subject like "Unusual spending of $1076 detected!"
