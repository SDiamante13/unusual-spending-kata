package spending;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spending.model.Category;
import spending.model.UnusualPayment;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;

class EmailFormatterTest {

    private final EmailFormatter emailFormatter = new EmailFormatter();

    @Test
    void givenUnusualPaymentsItFormatsEmailBody() {
        Set<UnusualPayment> unusualPayments = new HashSet<>(singletonList(
                new UnusualPayment(Category.GROCERIES, 148)
        ));

        String actualBody = emailFormatter.formatBody(unusualPayments);

        Assertions.assertThat(actualBody)
                .isEqualTo("Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                "* You spent $200 on groceries\n" +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company");
    }
}