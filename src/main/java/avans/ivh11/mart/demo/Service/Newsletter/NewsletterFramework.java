package avans.ivh11.mart.demo.Service.Newsletter;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import com.google.common.collect.Iterables;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public abstract class NewsletterFramework {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(NewsletterFramework.class);

    @Autowired
    private RegisteredUserRepository userRepository;

    public HashMap<String, Object> sendNewsletter(Newsletter newsletter) {
        HashMap<String, Object> results = new HashMap<>();

        Iterable<RegisteredUser> users = this.getAllUsers();
        if (users == null || Iterables.size(users) == 0) {
            results.put("error", "0 Receivers were found");
            return results;
        }

        List<RegisteredUser> recipients = this.getAllRecipients(users);
        if (recipients.size() == 0) {
            results.put("error", "0 Receivers were found");
            return results;
        }

        return this.sendingNewsletter(recipients, newsletter);
    }

    /**
     * find all Registered users
     *
     * @return
     */
    private Iterable<RegisteredUser> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * filters registered users who do not wish to receive a newsletter
     *
     * @param users
     * @return
     */
    private List<RegisteredUser> getAllRecipients(Iterable<RegisteredUser> users) {
        List<RegisteredUser> list = new ArrayList<>();
        for (RegisteredUser user : users) {
            if (user.isSubscribeToNewsletter()) {
                list.add(user);
            }
        }

        return list;
    }

    public abstract HashMap<String, Object> sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter);
}
