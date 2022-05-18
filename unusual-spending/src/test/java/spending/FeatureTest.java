package spending;

import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.Payment;

import java.time.Month;
import java.util.HashSet;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

class FeatureTest {

    public static final long USER_ID = 123L;
    public static final String SUBJECT = "Unusual spending of $148 detected!";
    public static final int THIS_MONTH = Month.MAY.getValue();
    public static final int LAST_MONTH = Month.APRIL.getValue();
    public static final int CURRENT_YEAR = 2022;

    private final PaymentService mockPaymentService = mock(PaymentService.class);
    private final EmailService mockEmailService = mock(EmailService.class);
    TriggersUnusualSpendingEmail triggersUnusualSpendingEmail = new TriggersUnusualSpendingEmail(mockPaymentService, mockEmailService);


    @Test
    void triggerUnusualSpendingEmail() {
        String expectedEmailBody = "Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                "* You spent $148 on groceries\n" +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company";
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, THIS_MONTH))
                .thenReturn(new HashSet<>(
                        singletonList(new Payment(200, "Food for yet another month", Category.GROCERIES))
                ));
        when(mockPaymentService.fetchPayments(USER_ID, CURRENT_YEAR, LAST_MONTH))
                .thenReturn(new HashSet<>(
                        singletonList(new Payment(50, "Food for month", Category.GROCERIES))
                ));

        triggersUnusualSpendingEmail.trigger(USER_ID);

        verify(mockEmailService).publish(USER_ID, SUBJECT, expectedEmailBody);
    }
}
