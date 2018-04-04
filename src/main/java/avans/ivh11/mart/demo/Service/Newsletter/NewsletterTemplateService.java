package avans.ivh11.mart.demo.Service.Newsletter;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Service.FlashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Service
public class NewsletterTemplateService {

    @Autowired
    private NewsletterFramework newsletterEmail;

    @Autowired
    private NewsletterFramework newsletterSMS;

    @Autowired
    private FlashService flashService;

    /**
     * Calling the right type of newsletter
     *
     * @param newsletter
     * @param redirect
     * @return
     */
    public RedirectAttributes handleNewsletter(Newsletter newsletter, RedirectAttributes redirect) {

        HashMap<String, Object> results = new HashMap<>();

        switch (newsletter.getType()) {
            case "email":
                results = this.newsletterEmail.sendNewsletter(newsletter);
                break;

            case "sms":
                results = this.newsletterSMS.sendNewsletter(newsletter);
                break;

            default:
                results.put("error", "Type " + newsletter.getType() + " is not supported...");
        }

        if (results.get("error") != null) {
            redirect.addFlashAttribute("flash", this.flashService.createFlash("danger", "Error - " + results.get("error")));
        }

        if (results.get("total") != null) {
            if (results.get("failure") != null && (int) results.get("failure") > 0) {
                redirect.addFlashAttribute("flash", this.flashService.createFlash("warning", results.get("success") + "/" + results.get("total") + " were send successfully"));
            } else {
                redirect.addFlashAttribute("flash", this.flashService.createFlash("success", results.get("total") + " newsletters were sent"));
            }
        }

        return redirect;
    }
}
