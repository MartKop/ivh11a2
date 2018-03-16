package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Service.ShoppingCardService;
import avans.ivh11.mart.demo.Service.ShoppingCartServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public ShoppingCardService shoppingCardService(){
        return new ShoppingCartServiceImpl();
    }

//    public NewsletterFramework newsletterEmail() {
//        return new NewsletterEmail();
//    }
//
//    @Bean
//    public NewsletterFramework newsletterSMS() {
//        return new NewsletterSMS();
//    }

}
