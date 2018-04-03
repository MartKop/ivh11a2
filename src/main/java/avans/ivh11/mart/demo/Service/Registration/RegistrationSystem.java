package avans.ivh11.mart.demo.Service.Registration;

import avans.ivh11.mart.demo.Domain.RegisteredUser;

import java.util.Enumeration;
import java.util.Vector;

public class RegistrationSystem {
    private Vector listeners = new Vector();

    public void register(RegistrationListener registrationListener) {
        listeners.addElement(registrationListener);
    }

    public void sendConfirmations(RegisteredUser user) {
        for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
            ((RegistrationListener) e.nextElement()).sendRegistrationConfirmation(user);
        }
    }
}
