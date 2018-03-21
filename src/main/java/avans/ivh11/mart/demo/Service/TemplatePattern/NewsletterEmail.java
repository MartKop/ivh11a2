package avans.ivh11.mart.demo.Service.TemplatePattern;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NewsletterEmail extends NewsletterFramework {

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public HashMap<String, Object> sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter) {
        HashMap<String, Object> results = new HashMap<>();
        int total = 0;
        int failure = 0;

        for (RegisteredUser user : recipients) {
            total += 1;
            try {

                Context ctx = new Context(new Locale("en"));
                ctx.setVariable("name", user.getFirstName());
                ctx.setVariable("subscriptionDate", new Date());
                ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
                String textContent = this.htmlTemplateEngine.process("mail/newsletter/newsletterTemplate", ctx);

                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(new InternetAddress("mkop@avans.nl", "Test Newsletter"));
                helper.setTo(new InternetAddress("mart-k15@hotmail.com", "Martyy"));
//                helper.setTo(new InternetAddress(user.getEmail(), user.getFullname()));
                helper.setSubject(newsletter.getBody());
                helper.setText(textContent,true);

                emailSender.send(message);

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
