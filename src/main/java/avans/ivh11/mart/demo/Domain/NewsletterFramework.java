package avans.ivh11.mart.demo.Domain;

import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class NewsletterFramework {

    @Autowired
    private RegisteredUserRepository userRepository;

    public boolean sendNewsletter(Newsletter newsletter) {
        Iterable<RegisteredUser> users = this.getAllUsers();
        if (users == null || Iterables.size(users) == 0) {
            return false;
        }

        List<RegisteredUser> recipients = this.getAllRecipients(users);
        return recipients.size() != 0 && this.sendingNewsletter(recipients, newsletter);

    }

    private Iterable<RegisteredUser> getAllUsers() {
        return userRepository.findAll();
    }

    private List<RegisteredUser> getAllRecipients(Iterable<RegisteredUser> users) {
        List<RegisteredUser> list = new ArrayList<>();
        for (RegisteredUser user : users) {
           if (user.isSubscribeToNewsletter()) {
               list.add(user);
           }
        }

        return list;
    }

    public abstract boolean sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter);

}
