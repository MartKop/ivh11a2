package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderOption;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.PaymentStrategy;
import avans.ivh11.mart.demo.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {

    @Autowired
    private PaymentStrategy paymentStrategy;

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
        String payment = paymentStrategy.payPalPayment(shoppingCartService.getTotal().toString());
        return new ModelAndView("redirect:" + payment);
    }

    @RequestMapping(value = "/payment/creditcard", method = RequestMethod.GET)
    public ModelAndView creditCardPayment(){
        return payment();
    }
}
