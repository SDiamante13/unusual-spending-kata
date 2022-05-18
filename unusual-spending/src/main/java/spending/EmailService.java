package spending;

public class EmailService {

    public void publish(long userId, String subject, String emailBody) {
        EmailsUser.email(userId, subject, emailBody);
    }
}
