package avans.ivh11.mart.demo.Service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}