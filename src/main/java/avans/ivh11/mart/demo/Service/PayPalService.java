//package avans.ivh11.mart.demo.Service;
//
//import com.paypal.api.payments.Amount;
//import com.paypal.api.payments.Payer;
//import com.paypal.api.payments.Payment;
//import com.paypal.api.payments.RedirectUrls;
//import com.paypal.api.payments.Transaction;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PayPalService {
//    Amount amount = new Amount();
//    amount.setCurrency("USD");
//    amount.setTotal("1.00");
//
//    Transaction transaction = new Transaction();
//    transaction.setAmount(amount);
//    List<Transaction> transactions = new ArrayList<Transaction>();
//    transactions.add(transaction);
//
//    Payer payer = new Payer();
//    payer.setPaymentMethod("paypal");
//
//    Payment payment = new Payment();
//    payment.setIntent("sale");
//    payment.setPayer(payer);
//    payment.setTransactions(transactions);
//
//    RedirectUrls redirectUrls = new RedirectUrls();
//    redirectUrls.setCancelUrl("https://example.com/cancel");
//    redirectUrls.setReturnUrl("https://example.com/return");
//    payment.setRedirectUrls(redirectUrls);
//}
