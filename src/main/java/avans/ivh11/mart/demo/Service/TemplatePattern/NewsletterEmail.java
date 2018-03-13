package avans.ivh11.mart.demo.Service.TemplatePattern;

import javax.mail.internet.InternetAddress;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NewsletterEmail extends NewsletterFramework {

    @Autowired
    private EmailService emailService;

    @Override
    public HashMap<String, Object> sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter) {
        HashMap<String, Object> results = new HashMap<>();
        int total = 0;
        int failure = 0;

        for (RegisteredUser user : recipients) {
            total += 1;
            try {
                final Email email = DefaultEmail.builder()
                        .from(new InternetAddress("mkop@avans.nl", "Mart test"))
//                        .to(Lists.newArrayList(new InternetAddress(user.getEmail(), user.getFullname())))
                        .to(Lists.newArrayList(new InternetAddress("mart-k15@hotmail.com", "Marty ")))
                        .subject(newsletter.getSubject())
                        .body(newsletter.getBody())
                        .encoding("UTF-8")
                        .build();

                emailService.send(email);

            } catch (Exception e) {
                failure += 1;
                logger.warn(e.getMessage());
                results.put("error", e.getMessage());
            }
        }

        //results.put("result", total != 0 && failure != 0);
        results.put("failure", failure);
        results.put("total", total);
        results.put("success", total - failure);

        return results;
    }
}
