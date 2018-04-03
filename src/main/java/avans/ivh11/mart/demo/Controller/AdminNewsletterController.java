package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.Newsletter.NewsletterTemplateService;
import avans.ivh11.mart.demo.Service.NewsletterConstraintService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/newsletter")
public class AdminNewsletterController {

    @Autowired
    private FlashService flashService;

    @Autowired
    private NewsletterConstraintService newsletterConstraintService;

    @Autowired
    private NewsletterTemplateService newsletterService;

    @Autowired
    public AdminNewsletterController() {
    }

    @GetMapping
    public ModelAndView newsletterForm(@ModelAttribute Newsletter newsletter) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Nieuwsbrief");
        mav.setViewName("views/newsletter/form");

        return mav;
    }

    @PostMapping()
    public ModelAndView sendNewsletter(@ModelAttribute Newsletter newsletter, BindingResult bindingResult, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Nieuwsbrief - Verstuur");

        bindingResult = newsletterConstraintService.newsletterCheck(bindingResult, newsletter);

        if (bindingResult.hasErrors()) {
            mav.addObject("form_errors", bindingResult.getAllErrors());
            mav.addObject("flash", this.flashService.createFlash("danger", "Nieuwsbrief was niet verstuurd. Fix je errors."));
            mav.setViewName("views/newsletter/form");

            return mav;
        }
        mav.setViewName("redirect:/admin/newsletter");

        redirect = this.newsletterService.handleNewsletter(newsletter, redirect);

        return mav;
    }
}
