package avans.ivh11.mart.demo.Service.ObserverPattern;

import avans.ivh11.mart.demo.Domain.RegisteredUser;

import java.util.List;

public interface RegistrationListener {
    public void sendRegistrationConfirmation(RegisteredUser user);
}
