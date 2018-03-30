package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderOption;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {

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
        ModelAndView modelAndView = new ModelAndView("views/payment/checkout");
        if(shoppingCartService.getTotal().toString().equals("0.0")) {
            modelAndView.addObject("title", "Winkelwagen is leeg, selecteer een of meerdere producten aub.");
        } else {
            modelAndView.addObject("title", "Betaling");
            modelAndView.addObject("total", "Totaalbedrag: " + shoppingCartService.getTotal().toString());
        }
        return modelAndView;
    }


}
