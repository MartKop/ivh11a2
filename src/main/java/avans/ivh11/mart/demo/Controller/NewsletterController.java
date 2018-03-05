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

@Controller
@RequestMapping("/admin/newsletter")
public class NewsletterController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private FlashService flashService;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private NewsletterConstraintService newsletterConstraintService;

    @Autowired
    private NewsletterEmail newsletterEmail;

    @Autowired
    private NewsletterSMS newsletterSMS;

    @Autowired
    public NewsletterController() {}

    @GetMapping
    public ModelAndView newsletterForm(@ModelAttribute Newsletter newsletter) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Newsletter - List");
        mav.setViewName("views/newsletter/form");

        return mav;
    }

    @PostMapping()
    public ModelAndView sendNewsletter(@ModelAttribute Newsletter newsletter, BindingResult bindingResult, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        bindingResult = newsletterConstraintService.newsletterCheck(bindingResult, newsletter);

        if (bindingResult.hasErrors()) {
            mav.addObject("form_errors", bindingResult.getAllErrors());
            mav.addObject("flash", this.flashService.createFlash("danger", "Newsletter was not sent. Fix the errors and try again."));
            mav.setViewName("views/newsletter/form");

            return mav;
        }

//        boolean sent = this.newsletterEmail.sendNewsletter(newsletter);
        boolean sent = this.newsletterSMS.sendNewsletter(newsletter);

        if(sent) {
            redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Email was sent"));
        } else {
            redirect.addFlashAttribute("flash", this.flashService.createFlash("warning", "Email was not sent"));
        }


        mav.setViewName("redirect:/admin/newsletter");
        //redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully sent a newsletter"));

        return mav;
    }



}
