package spending;

import spending.model.UnusualPayment;

import java.util.Set;

public class EmailFormatter {
    public String formatBody(Set<UnusualPayment> unusualPayments) {
        return "Hello card user!\n" +
                "\n" +
                "We have detected unusually high spending on your card in these categories:\n" +
                "\n" +
                "* You spent $200 on groceries\n" +
                "\n" +
                "Love,\n" +
                "\n" +
                "The Credit Card Company";
    }
}
