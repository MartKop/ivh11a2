package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Service.TemplatePattern.NewsletterSMS;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.LoggerFactory;

public class SMSSender {
    public final org.slf4j.Logger logger = LoggerFactory.getLogger(SMSSender.class);

    //THESE ARE PRODUCTION CREDENTIALS
//    public static final String ACCOUNT_SID = "ACca192ebce23f2dffed78bb23e9ddbbf3";
//    public static final String FROM = "+3197014200826";
//    public static final String AUTH_TOKEN = "289ac7db647dd82b5cf957311d55c428";

    //THESE ARE TEST CREDENTIALS
    public static final String ACCOUNT_SID = "AC994964b3d9e7aed3be62600c6a0943f6";
    public static final String AUTH_TOKEN = "e2bc0065642db0096b7b18b455361031";
    public static final String FROM = "+15005550006";
//    public static final String FROM_INVALID = "+15005550001";
//    public static final String FROM_SMS_INCAPABLE = "+15005550007";
//    public static final String FROM_FULL_QUEUE = "+15005550008";

    public static final String TO = "+31634888673";

    public boolean sendSMS(RegisteredUser recipient, String body) {
        try {
            this.createTwilioObject(recipient.getPhone(), body);
        } catch (TwilioException e) {
            logger.warn(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean sendBulkSMS(Iterable<RegisteredUser> recipients, String body) {
        int total = 0;
        int failure = 0;

        for (RegisteredUser recipient : recipients) {
            total += 1;
            try {
                this.createTwilioObject(recipient.getPhone(), body);
            } catch (TwilioException e) {
                failure += 1;
                logger.warn(e.getMessage());
            }
        }

        return total > 0 && failure != 0;
    }

    private void createTwilioObject(String phoneNumber, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
//                new PhoneNumber(phoneNumber),
                new PhoneNumber(TO),
                new PhoneNumber(FROM),
                body
        ).create();
        this.logger.info(message.getSid());
    }
}
