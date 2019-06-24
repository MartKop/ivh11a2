package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderState.OrderPaidState;
import avans.ivh11.mart.demo.Repository.OrderStateRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Service.IPaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    @Autowired
    private IPaymentStrategy payPalPayment;

    @Autowired
    private IPaymentStrategy creditCardPayment;

    @Autowired
    private BaseOrderRepository<Order> orderRepository;

    @Autowired
    private FlashService flashService;

    @Autowired
    private OrderStateRepository orderStateRepository;

    @GetMapping("/payment/{id}")
    public ModelAndView payment(@PathVariable Long id){
        Order shoppingcart = orderRepository.findOne(id);
        ModelAndView mav = new ModelAndView("views/payment/checkout");
        mav.addObject("title", "Betaling");
        mav.addObject("paypal", "Betaal met PayPal");
        mav.addObject("total", "Totaalbedrag: " + String.valueOf(shoppingcart.price()));
        mav.addObject("creditcard", "Betaal met creditcard.");
        mav.addObject("id", shoppingcart.getId());
        return mav;
    }

    @RequestMapping(value = "/payment/paypal/{id}")
    public ModelAndView payPalPayment(@PathVariable Long id, RedirectAttributes redirect) {
        Order shoppingcart = orderRepository.findOne(id);
        String url = payPalPayment.pay(String.valueOf(shoppingcart.price()));
        ModelAndView mav = new ModelAndView("redirect:/welcome");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Betaling met paypal gelukt!"));
        orderStateRepository.delete(shoppingcart.getOrderState().getId());
        shoppingcart.setOrderState(new OrderPaidState(shoppingcart));
        return mav;
    }

    @RequestMapping(value = "/payment/creditcard/{id}", method = RequestMethod.GET)
    public ModelAndView creditCardPayment(@PathVariable Long id, RedirectAttributes redirect){
        Order shoppingcart = orderRepository.findOne(id);
        creditCardPayment.pay(String.valueOf(shoppingcart.price()));
        ModelAndView mav = new ModelAndView("redirect:/welcome");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Betaling met creditcard gelukt!"));
        orderStateRepository.delete(shoppingcart.getOrderState().getId());
        shoppingcart.setOrderState(new OrderPaidState(shoppingcart));
        return mav;
    }
}
