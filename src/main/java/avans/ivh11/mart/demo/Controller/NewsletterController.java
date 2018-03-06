package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Domain.NewsletterEmail;
import avans.ivh11.mart.demo.Domain.NewsletterSMS;
import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.NewsletterConstraintService;
import avans.ivh11.mart.demo.Service.NewsletterTemplateService;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.InternetAddress;
import java.util.HashMap;

@Controller
@RequestMapping("/admin/newsletter")
public class NewsletterController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private FlashService flashService;

    @Autowired
    private NewsletterConstraintService newsletterConstraintService;

    @Autowired
    private NewsletterTemplateService newsletterService;

    @Autowired
    public NewsletterController() {}

    @GetMapping
    public ModelAndView newsletterForm(@ModelAttribute Newsletter newsletter) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Newsletter - Create");
        mav.setViewName("views/newsletter/form");

        return mav;
    }

    @PostMapping()
    public ModelAndView sendNewsletter(@ModelAttribute Newsletter newsletter, BindingResult bindingResult, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Newsletter - Create");

        bindingResult = newsletterConstraintService.newsletterCheck(bindingResult, newsletter);

        if (bindingResult.hasErrors()) {
            mav.addObject("form_errors", bindingResult.getAllErrors());
            mav.addObject("flash", this.flashService.createFlash("danger", "Newsletter was not sent. Fix the errors and try again."));
            mav.setViewName("views/newsletter/form");

            return mav;
        }
        mav.setViewName("redirect:/admin/newsletter");

        redirect = this.newsletterService.handleNewsletter(newsletter, redirect);

        return mav;
    }
}
