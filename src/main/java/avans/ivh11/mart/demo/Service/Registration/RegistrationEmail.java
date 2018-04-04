package avans.ivh11.mart.demo.Service.Registration;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
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
    public JavaMailSender emailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;

    public RegistrationEmail(TemplateEngine htmlTemplateEngine, JavaMailSender emailSender) {
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.emailSender = emailSender;
    }

    public RegistrationEmail() {
    }

    /**
     * Sends a registration confirmation e-mail to a registered user
     *
     * @param user
     */
    @Override
    public void sendRegistrationConfirmation(RegisteredUser user) {
        try {
            Context ctx = new Context(new Locale("nl"));
            ctx.setVariable("user", user);
            ctx.setVariable("date", new Date());
            String textContent = this.htmlTemplateEngine.process("/mail/registration/registrationTemplate", ctx);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(new InternetAddress("mart-k15@hotmail.com", "Test Registration"));
            helper.setTo(new InternetAddress(user.getEmail(), user.getFullName()));
            helper.setSubject("Registratie webshop IVH11A2");
            helper.setText(textContent, true);

            emailSender.send(message);

        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }
}
