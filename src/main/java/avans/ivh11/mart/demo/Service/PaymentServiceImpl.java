package avans.ivh11.mart.demo.Service;

import com.paypal.api.payments.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class PaymentServiceImpl implements PaymentService {

    public Payment payPalPayment(String price) {

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
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/");
        redirectUrls.setReturnUrl("http://localhost:8080/");
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }
}
