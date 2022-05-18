package spending;

public class TriggersUnusualSpendingEmail {

	private final PaymentService paymentService;
	private final EmailService emailService;

	public TriggersUnusualSpendingEmail(PaymentService paymentService, EmailService emailService) {
		this.paymentService = paymentService;
		this.emailService = emailService;
	}

	public void trigger(long userId) {
		// TODO: This is the entry point. Start with a test of this class
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
