package avans.ivh11.mart.demo.Service.TemplatePattern;

import avans.ivh11.mart.demo.Domain.Newsletter;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Service.SMSSender;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NewsletterSMS extends NewsletterFramework {

    @Autowired
    private SMSSender sender;

    @Override
    public HashMap<String, Object> sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter) {
        HashMap<String, Object> results = new HashMap<>();
        int total = 0;
        int failure = 0;

        for (RegisteredUser user : recipients) {
            total += 1;
            if(!this.sender.sendSMS(user, newsletter.getBody())) {
                failure += 1;
            }
        }

        //results.put("result", total != 0 && failure != 0);
        results.put("failure", failure);
        results.put("total", total);
        results.put("success", total - failure);

        return results;
    }
}
