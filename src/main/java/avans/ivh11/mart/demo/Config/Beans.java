package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Service.ObserverPattern.RegistrationEmail;
import avans.ivh11.mart.demo.Service.ObserverPattern.RegistrationListener;
import avans.ivh11.mart.demo.Service.ObserverPattern.RegistrationSMS;
import avans.ivh11.mart.demo.Service.ObserverPattern.RegistrationSystem;
import avans.ivh11.mart.demo.Service.ShoppingCardService;
import avans.ivh11.mart.demo.Service.ShoppingCartServiceImpl;
import avans.ivh11.mart.demo.Service.SMSSender;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterEmail;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterFramework;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterSMS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public ShoppingCardService shoppingCardService(){
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
    public JavaMailSender emailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("in-v3.mailjet.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("9b64f11f41684523c392969e5e1ace2c");
        javaMailSender.setPassword("a1b38108c24652320dc4ff427e45e37d");
        javaMailSender.setJavaMailProperties(this.javaMailProperties());

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

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for HTML emails (except the editable one)
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Resolver for HTML editable emails (which will be treated as a String)
//        templateEngine.addTemplateResolver(stringTemplateResolver());
//        // Message source, internationalization specific to emails
//        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(0);
//        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
//        templateResolver.setPrefix("/mail/");
//        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public RegistrationSystem registrationSystem() {
        RegistrationSystem registrationSystem = new RegistrationSystem();
        registrationSystem.register(new RegistrationEmail(new TemplateEngine(), this.emailSender()));
        registrationSystem.register(new RegistrationSMS(this.sender()));

        return registrationSystem;
    }
}
