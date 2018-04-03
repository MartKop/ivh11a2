package avans.ivh11.mart.demo.Service.Registration;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Service.SMSSender;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationSMS implements RegistrationListener {

    private final String text = "Bedankt voor je registratie bij de webwinkel van 23IVH11A2!";
    @Autowired
    private SMSSender sender;

    public RegistrationSMS(SMSSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendRegistrationConfirmation(RegisteredUser user) {
        this.sender.sendSMS(user, text);
    }
}
