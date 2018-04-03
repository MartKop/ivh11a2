package avans.ivh11.mart.demo.Service.ObserverPattern;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterFramework;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Locale;

public class RegistrationEmail implements RegistrationListener {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(RegistrationEmail.class);

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    public JavaMailSender emailSender;

    public RegistrationEmail(TemplateEngine htmlTemplateEngine, JavaMailSender emailSender) {
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.emailSender = emailSender;
    }

    public RegistrationEmail() {}

    /**
     * Sends a registration confirmation e-mail to a registered user
     *
     * @param user
     */
    @Override
    public void sendRegistrationConfirmation(RegisteredUser user) {
        try {
            Context ctx = new Context(new Locale("en"));
            ctx.setVariable("user", user);
            ctx.setVariable("date", new Date());
            String textContent = this.htmlTemplateEngine.process("/mail/registration/registrationTemplate", ctx);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(new InternetAddress("mkop@avans.nl", "Test Registration"));
            helper.setTo(new InternetAddress("mart-k15@hotmail.com", "Martyy"));
//                helper.setTo(new InternetAddress(user.getEmail(), user.getFullname()));
            helper.setSubject("Registration webshop confirmation");
            helper.setText(textContent,true);

            emailSender.send(message);

        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }
}
