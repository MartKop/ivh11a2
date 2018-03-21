package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import avans.ivh11.mart.demo.Repository.UnregisteredUserRepository;
import avans.ivh11.mart.demo.Service.SMSSender;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterEmail;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterFramework;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterSMS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class Beans {

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

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
    public JavaMailSender emailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("in-v3.mailjet.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("9b64f11f41684523c392969e5e1ace2c");
        javaMailSender.setPassword("a1b38108c24652320dc4ff427e45e37d");
        javaMailSender.setJavaMailProperties(javaMailProperties());
        return javaMailSender;
    }

    private Properties javaMailProperties(){
        Properties properties = new Properties();
        // add more properties in the same way
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.debug", "true");
        return properties;
    }
}
