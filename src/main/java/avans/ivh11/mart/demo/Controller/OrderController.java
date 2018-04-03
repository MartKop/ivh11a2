package avans.ivh11.mart.demo.Controller;


import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private FlashService flashService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ModelAndView orderDetails(@PathVariable("id") Long orderId){
            Order order = orderService.getOrderDetails(orderId);
            Float totalPrice = orderService.getTotalPrice(order);
            ModelAndView mav = new ModelAndView("views/order/orderDetails");
            mav.addObject("order", order);
            mav.addObject("products", order.getProducts());
            mav.addObject("title", "Orderdetails");
            mav.addObject("totalPrice", totalPrice);
            return mav;
    }


    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") Long orderId, RedirectAttributes redirect){
        Order order = orderService.getOrderDetails(orderId);
        if(order.canCancel()){
            orderService.cancelOrder(order);
            redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Order is gecancelled."));
            return "redirect:/user/" + order.getUser().getId();
        }else{
            redirect.addFlashAttribute("flash", this.flashService.createFlash("error", "Kan order niet cancellen op dit moment, Wacht op volgende status."));
            return "redirect:/order/" + order.getId();
        }




    }


}
