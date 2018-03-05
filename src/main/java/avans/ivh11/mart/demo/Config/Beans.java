package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Domain.NewsletterEmail;
import avans.ivh11.mart.demo.Domain.NewsletterSMS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public NewsletterEmail newsletterEmail() {
        return new NewsletterEmail();
    }

    @Bean
    public NewsletterSMS newsletterSMS() {
        return new NewsletterSMS();
    }
}
