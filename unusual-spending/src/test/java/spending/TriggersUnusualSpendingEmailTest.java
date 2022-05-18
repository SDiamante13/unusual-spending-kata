package spending;

import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.Payment;
import spending.model.UnusualPayment;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;
import static spending.FeatureTest.*;

class TriggersUnusualSpendingEmailTest {

    private final Moment mockMoment = mock(Moment.class);
    private final PaymentService mockPaymentService = mock(PaymentService.class);
    private final Detector mockDetector = mock(Detector.class);
    private final SpendingCalculator mockSpendingCalculator = mock(SpendingCalculator.class);
    private final EmailFormatter mockEmailFormatter = mock(EmailFormatter.class);
    private final EmailService mockEmailService = mock(EmailService.class);

    private final TriggersUnusualSpendingEmail triggersUnusualSpendingEmail =
            new TriggersUnusualSpendingEmail(mockMoment, mockPaymentService, mockDetector, mockSpendingCalculator, mockEmailFormatter, mockEmailService);

    @Test
    void triggersEmailForUnusualSpendingForTheGivenUserId() {
        String expectedEmailBody = "Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                "* You spent $148 on groceries\n" +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company";
        Set<Payment> thisMonthsPayments = new HashSet<>(
                singletonList(new Payment(200, "Food for yet another month", Category.GROCERIES))
        );
        when(mockMoment.now())
                .thenReturn(LocalDate.of(2022, Month.FEBRUARY, 1));
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, THIS_MONTH))
                .thenReturn(thisMonthsPayments);
        Set<Payment> lastMonthsPayments = new HashSet<>(
                singletonList(new Payment(50, "Food for month", Category.GROCERIES))
        );
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, LAST_MONTH))
                .thenReturn(lastMonthsPayments);
        HashSet<UnusualPayment> unusualPayments = new HashSet<>(singletonList(new UnusualPayment(Category.GROCERIES, 148)));
        when(mockDetector.detectUnusualPayments(thisMonthsPayments, lastMonthsPayments))
                .thenReturn(unusualPayments);
        when(mockSpendingCalculator.calculateTotalAmountOfUnusualSpending(unusualPayments))
                .thenReturn(148);
        when(mockEmailFormatter.formatBody(unusualPayments))
                .thenReturn(expectedEmailBody);

        triggersUnusualSpendingEmail.trigger(USER_ID);

        verify(mockEmailService).publish(USER_ID, SUBJECT, expectedEmailBody);
    }
}