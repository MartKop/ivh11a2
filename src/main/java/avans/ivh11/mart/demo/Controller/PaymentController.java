package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderOption;
import avans.ivh11.mart.demo.Service.PayPalStrategy;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.PaymentStrategy;
import avans.ivh11.mart.demo.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {

    @Autowired
    private PaymentStrategy payPalPayment;

    @Autowired
    private PaymentStrategy creditCardPayment;

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
            mav.addObject("creditcard", "Betaal met creditcard.");
        }
        return mav;
    }

    @RequestMapping(value = "/payment/paypal")
    public ModelAndView payPalPayment(){
        String url = payPalPayment.pay(shoppingCartService.getTotal().toString());
        shoppingCartService.clearProducts();
        return new ModelAndView("redirect:" + url);
    }

    @RequestMapping(value = "/payment/creditcard", method = RequestMethod.GET)
    public ModelAndView creditCardPayment(){
        creditCardPayment.pay(shoppingCartService.getTotal().toString());
        shoppingCartService.clearProducts();
        return new ModelAndView("redirect:/");
    }
}
