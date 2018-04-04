package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Newsletter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class NewsletterConstraintService {

    /**
     * checks for constraint violations based on type
     *
     * @param bindingResult
     * @param newsletter
     * @return
     */
    public BindingResult newsletterCheck(BindingResult bindingResult, Newsletter newsletter) {
        switch (newsletter.getType()) {
            case "email":
                bindingResult = this.doEmail(bindingResult, newsletter);
                break;

            case "sms":
                bindingResult = this.doSMS(bindingResult, newsletter);
                break;

            default:
                bindingResult.addError(new ObjectError("Undefined type", "There is an error in the type of newsletter."));
                break;
        }

        return bindingResult;
    }

    /**
     * @param bindingResult
     * @param newsletter
     * @return
     */
    private BindingResult doEmail(BindingResult bindingResult, Newsletter newsletter) {
        if (newsletter.getSubject().isEmpty()) {
            bindingResult.addError(
                    new ObjectError(
                            "Subject is empty",
                            "The subject of an email cannot be empty. Please fill this in."
                    )
            );
        }

        int minLength = 25;
        if (newsletter.getBody().length() < minLength) {
            bindingResult.addError(
                    new ObjectError(
                            "Content is too little",
                            "The content of an email cannot be " + newsletter.getBody().length() + " characters long. Please make is at least " + minLength + " characters long."
                    )
            );
        }

        return bindingResult;
    }

    /**
     * @param bindingResult
     * @param newsletter
     * @return
     */
    private BindingResult doSMS(BindingResult bindingResult, Newsletter newsletter) {
        if (newsletter.getBody().length() >= 160 || newsletter.getBody().length() < 5) {
            bindingResult.addError(
                    new ObjectError(
                            "body",
                            "The content of the SMS must contain 5-159 characters maximum. Currently " + newsletter.getBody().length() + " characters are used"
                    )
            );
        }

        return bindingResult;
    }
}
