package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Service.SMSSender;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterEmail;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterFramework;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterSMS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public SMSSender sender() {
        return new SMSSender();
    }
}
