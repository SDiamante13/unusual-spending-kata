package spending;

import spending.model.UnusualPayment;

import java.util.Set;
import java.util.stream.Collectors;

public class EmailFormatter {
    public String formatBody(Set<UnusualPayment> unusualPayments) {
        String lineItems = unusualPayments.stream()
                .map(up -> "* You spent $" + up.amount() + " on " + up.category().alias() + "\n")
                .collect(Collectors.joining(""));

        return "Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                lineItems +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company";
    }
}
