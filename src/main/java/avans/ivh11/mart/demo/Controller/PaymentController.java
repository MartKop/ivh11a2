package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderOption;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.PaymentService;
import avans.ivh11.mart.demo.Service.ShoppingCartService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private BaseOrderRepository<Order> orderRepository;
    @Autowired
    private BaseOrderRepository<OrderOption> orderOptionRepository;

    @GetMapping("/payment")
    public ModelAndView payment(){
        ModelAndView mav = new ModelAndView("views/payment/checkout");
        if(shoppingCartService.getTotal().toString().equals("0.0")) {
            mav.addObject("title", "Winkelwagen is leeg, selecteer een of meerdere producten aub.");
        } else {
            mav.addObject("title", "Betaling");
            mav.addObject("paypal", "Betaal met PayPal");
            mav.addObject("total", "Totaalbedrag: " + shoppingCartService.getTotal().toString());
        }
        return mav;
    }

    @RequestMapping(value = "/payment/paypal", method = RequestMethod.GET)
    public ModelAndView payPalPayment(){
        // Create paypalpayment
        Payment payment = paymentService.payPalPayment(shoppingCartService.getTotal().toString());

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
                    return new ModelAndView("redirect:" + link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }

        return payment();
    }

    @RequestMapping(value = "/payment/creditcard", method = RequestMethod.GET)
    public ModelAndView creditCardPayment(){
        return payment();
    }
}
