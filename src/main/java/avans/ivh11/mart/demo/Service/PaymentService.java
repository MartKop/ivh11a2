package avans.ivh11.mart.demo.Service;

import com.paypal.api.payments.Payment;

public interface PaymentService {
    Payment payPalPayment(String price);
}
