package avans.ivh11.mart.demo.Service.ObserverPattern;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Service.SMSSender;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationSMS implements RegistrationListener {

    @Autowired
    private SMSSender sender;

    private final String text = "Thank you for your registration!";

    public RegistrationSMS(SMSSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendRegistrationConfirmation(RegisteredUser user) {
        this.sender.sendSMS(user, text);
    }
}
