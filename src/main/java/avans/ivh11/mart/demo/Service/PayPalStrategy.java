package avans.ivh11.mart.demo.Service;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PayPalStrategy extends avans.ivh11.mart.demo.Service.Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String price;

    public PayPalStrategy(String price){
        this.price = price;
    }

    @Override
    public String pay(String price) {

        // Payment ammount
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(price);

        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Betaling van computeronderdelen");

        // Add transaction to list
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // Add payment details
        com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/");
        redirectUrls.setReturnUrl("http://localhost:8080/");
        payment.setRedirectUrls(redirectUrls);

        try {
            // Sandbox api credentials.
            APIContext apiContext = new APIContext(
                    "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS",
                    "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL",
                    "sandbox");
            Payment createdPayment = payment.create(apiContext);
            System.out.println(createdPayment.toString());

            Iterator links = createdPayment.getLinks().iterator();

            while (links.hasNext()){
                Links link = (Links) links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")){
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
            return "/";
        }
        return "/";
    }
}
