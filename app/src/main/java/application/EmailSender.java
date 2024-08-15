package application;

import framework.annotations.Async;
import framework.annotations.ConfigurationProperties;
import framework.annotations.Service;

@Service
@ConfigurationProperties(prefix = "email")
public class EmailSender implements IEmailSender {

    private String to;
    private String from;

    @Async
    public void sendEmail(String content) {
        System.out.println("Email sent: " + content);
        System.out.println("Email to: " + to);
        System.out.println("Email from: " + from);
    }

}
