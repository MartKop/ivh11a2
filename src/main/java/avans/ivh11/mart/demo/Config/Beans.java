package avans.ivh11.mart.demo.Config;


import avans.ivh11.mart.demo.Service.*;
import avans.ivh11.mart.demo.Service.Newsletter.NewsletterEmail;
import avans.ivh11.mart.demo.Service.Newsletter.NewsletterFramework;
import avans.ivh11.mart.demo.Service.Newsletter.NewsletterSMS;
import avans.ivh11.mart.demo.Service.Registration.RegistrationEmail;
import avans.ivh11.mart.demo.Service.Registration.RegistrationSMS;
import avans.ivh11.mart.demo.Service.Registration.RegistrationSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@Configuration
public class Beans {

    @Bean
    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

    @Bean
    public PaymentStrategy payPalPayment() { return new PayPalStrategy(); }

    @Bean
    public PaymentStrategy creditCardPayment() {return new CreditCardStrategy(); }

    @Bean
    public JavaMailSender emailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("in-v3.mailjet.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("090932d25e2f525715226729438e6c9e");
        javaMailSender.setPassword("ce387831b0760bfc431f5062eb004f83");
        javaMailSender.setJavaMailProperties(this.javaMailProperties());

        return javaMailSender;
    }

    private Properties javaMailProperties() {
        Properties properties = new Properties();
        // add more properties in the same way
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.debug", "true");

        return properties;
    }

    @Bean
    public RegistrationSystem registrationSystem() {
        RegistrationSystem registrationSystem = new RegistrationSystem();
        registrationSystem.register(this.registrationEmail());
        registrationSystem.register(new RegistrationSMS(this.sender()));

        return registrationSystem;
    }

    @Bean
    public RegistrationEmail registrationEmail() {
        return new RegistrationEmail();
    }
}
