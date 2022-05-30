package spending;

import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.Payment;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

class FeatureTest {

    public static final long USER_ID = 123L;
    public static final int THIS_MONTH = Month.FEBRUARY.getValue();
    public static final int LAST_MONTH = Month.JANUARY.getValue();
    public static final int CURRENT_YEAR = 2022;

    private final Moment mockMoment = mock(Moment.class);
    private final PaymentService mockPaymentService = mock(PaymentService.class);
    private final EmailService mockEmailService = mock(EmailService.class);
    TriggersUnusualSpendingEmail triggersUnusualSpendingEmail =
            new TriggersUnusualSpendingEmail(mockMoment, mockPaymentService, new Detector(), new SpendingCalculator(), new EmailFormatter(), mockEmailService);


    @Test
    void triggerUnusualSpendingEmailForSingleCategory() {
        String expectedEmailBody = "Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                "* You spent $200 on groceries\n" +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company";
        when(mockMoment.now())
                .thenReturn(LocalDate.of(2022, Month.FEBRUARY, 1));
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, THIS_MONTH))
                .thenReturn(new HashSet<>(
                        singletonList(new Payment(200, "Food for yet another month", Category.GROCERIES))
                ));
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, LAST_MONTH))
                .thenReturn(new HashSet<>(
                        singletonList(new Payment(50, "Food for month", Category.GROCERIES))
                ));

        triggersUnusualSpendingEmail.trigger(USER_ID);

        verify(mockEmailService).publish(USER_ID, "Unusual spending of $200 detected!", expectedEmailBody);
    }

    @Test
    void triggerUnusualSpendingEmailForMultipleCategories() {
        String expectedEmailBody = "Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                "* You spent $450 on entertainment\n" +
                "* You spent $200 on groceries\n" +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company";
        when(mockMoment.now())
                .thenReturn(LocalDate.of(2022, Month.FEBRUARY, 1));
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, THIS_MONTH))
                .thenReturn(new HashSet<>(
                        asList(
                                new Payment(100, "Food for yet another month", Category.GROCERIES),
                                new Payment(100, "More food for yet another month", Category.GROCERIES),
                                new Payment(250, "Entertainment for yet another month", Category.ENTERTAINMENT),
                                new Payment(200, "Entertainment for yet another month", Category.ENTERTAINMENT)
                        )
                ));
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, LAST_MONTH))
                .thenReturn(new HashSet<>(
                        asList(
                                new Payment(50, "Food for month", Category.GROCERIES),
                                new Payment(200, "Food for month", Category.ENTERTAINMENT)
                        )
                ));

        triggersUnusualSpendingEmail.trigger(USER_ID);

        verify(mockEmailService).publish(USER_ID, "Unusual spending of $650 detected!", expectedEmailBody);
    }
}
