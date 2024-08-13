package application;

import framework.annotations.Async;
import framework.annotations.Service;

@Service
public class SmsSender implements ISmsSender {
    @Async
    public void sendSMS(String content) {
        System.out.println("sending sms: "+content);
    }
}
