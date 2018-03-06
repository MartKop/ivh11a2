package avans.ivh11.mart.demo.Domain;

import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.LoggerFactory;

public class NewsletterSMS extends NewsletterFramework {

    //THESE ARE PRODUCTION CREDENTIALS
    public static final String ACCOUNT_SID = "ACca192ebce23f2dffed78bb23e9ddbbf3";
    public static final String FROM = "+3197014200826";
    public static final String AUTH_TOKEN = "289ac7db647dd82b5cf957311d55c428";

    //THESE ARE TEST CREDENTIALS
//    public static final String ACCOUNT_SID = "AC994964b3d9e7aed3be62600c6a0943f6";
//    public static final String AUTH_TOKEN = "e2bc0065642db0096b7b18b455361031";
//    public static final String FROM = "+15005550006";
//    public static final String FROM_INVALID = "+15005550001";
//    public static final String FROM_SMS_INCAPABLE = "+15005550007";
//    public static final String FROM_FULL_QUEUE = "+15005550008";

    public static final String TO = "+31634888673";

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(NewsletterSMS.class);

    @Override
    public boolean sendingNewsletter(Iterable<RegisteredUser> recipients, Newsletter newsletter) {
        boolean success = true;

        for (RegisteredUser user : recipients) {
            try {
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
//                        new PhoneNumber(user.getPhone()),
                        new PhoneNumber(TO),
                        new PhoneNumber(FROM),
                        newsletter.getBody()
                ).create();
                logger.info(message.getSid());
            } catch (TwilioException e) {
                success = false;
                logger.warn(e.getMessage());
            }
        }

        return success;
    }
}

