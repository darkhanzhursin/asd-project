package application;

import framework.annotations.Profile;
import framework.annotations.Service;
import framework.annotations.Value;

@Service
@Profile(value="Two")
public class SecureEmailSenderImpl implements EmailSender{
	@Value(name="message")
	String theMessage;

	public void sendEmail(String content) {
		System.out.println("sending secure email: "+content+" , message="+theMessage);
		
	}

}
