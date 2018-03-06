package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Domain.NewsletterEmail;
import avans.ivh11.mart.demo.Domain.NewsletterFramework;
import avans.ivh11.mart.demo.Domain.NewsletterSMS;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.InlinePicture;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.defaultimpl.DefaultEmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@Configuration
public class Beans {

    @Bean
    public NewsletterFramework newsletterEmail() {
        return new NewsletterEmail();
    }

    @Bean
    public NewsletterFramework newsletterSMS() {
        return new NewsletterSMS();
    }
}
