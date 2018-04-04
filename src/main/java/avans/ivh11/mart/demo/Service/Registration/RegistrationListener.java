package avans.ivh11.mart.demo.Service.Registration;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistrationListener {
    public void sendRegistrationConfirmation(RegisteredUser user);
}
