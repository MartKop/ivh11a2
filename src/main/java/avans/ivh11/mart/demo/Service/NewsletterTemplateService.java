package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Domain.NewsletterEmail;
import avans.ivh11.mart.demo.Domain.NewsletterFramework;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

@Service
public class NewsletterTemplateService {

    @Autowired
    private EmailService emailService;

    public boolean handleNewsletter(Newsletter newsletter) {


        return true;
    }
}
