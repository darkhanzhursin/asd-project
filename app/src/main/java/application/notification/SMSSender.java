package application.notification;

import framework.annotations.ConfigurationProperties;
import framework.annotations.Service;

@Service
@ConfigurationProperties(prefix = "sms")
public class SMSSender {

    private String to;
    private String from;

    public void sendSMS(){
        System.out.println("Sending SMS");
        System.out.println("To: " + to);
        System.out.println("From: " + from);
    }
}
