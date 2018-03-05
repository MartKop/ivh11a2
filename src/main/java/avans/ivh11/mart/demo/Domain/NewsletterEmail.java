package avans.ivh11.mart.demo.Domain;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage;

import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.Properties;

public class NewsletterEmail extends NewsletterFramework {

    @Autowired
    private EmailService emailService;

    @Override
    public boolean sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter) {
        boolean success = true;

        for (RegisteredUser user : recipients) {
            try {
                final Email email = DefaultEmail.builder()
                        .from(new InternetAddress("mkop@avans.nl", "Mart test"))
                        .to(Lists.newArrayList(new InternetAddress(user.getEmail(), user.getFullname())))
                        .to(Lists.newArrayList(new InternetAddress("mart-k15@hotmail.com", "Marty ")))
                        .subject(newsletter.getSubject())
                        .body(newsletter.getBody())
                        .encoding("UTF-8")
                        .build();

                emailService.send(email);
            } catch (Exception e) {
                success = false;
            }
        }

        return success;
    }
}
