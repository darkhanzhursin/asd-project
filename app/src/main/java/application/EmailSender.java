package application;

import framework.annotations.Async;
import framework.annotations.ConfigurationProperties;
import framework.annotations.Service;

@Service
public class EmailSender implements IEmailSender {

    @Async
    public void sendEmail(String content) {
        System.out.println("Email sent: " + content);
    }

}
