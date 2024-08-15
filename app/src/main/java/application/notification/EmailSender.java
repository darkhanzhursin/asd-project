package application.notification;

import framework.annotations.Async;
import framework.annotations.Service;

@Service
public class EmailSender implements ISender {

    @Async
    public void sendEmail(String content) {
        System.out.println("Email sent: " + content);
    }

}
