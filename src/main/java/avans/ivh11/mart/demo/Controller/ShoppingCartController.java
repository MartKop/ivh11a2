package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.*;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.ShoppingCardService;
import avans.ivh11.mart.demo.Service.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ShoppingCartController {
//    RegisteredUser user1 = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCardService shoppingCardService;

    @Autowired
    private BaseOrderRepository<Order> orderRepository;
    @Autowired
    private BaseOrderRepository<OrderOption> orderOptionRepository;


    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("views/shoppingCart/shoppingCart");
        modelAndView.addObject("products", shoppingCardService.getProductsInCart());
        modelAndView.addObject("total", shoppingCardService.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        shoppingCardService.addProduct(productRepository.findOne(productId));
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        shoppingCardService.removeProduct(productRepository.findOne(productId));
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/goToPayment")
    public ModelAndView goToPayment(){
        if(shoppingCardService.getSize() > 0){
            ModelAndView mav = new ModelAndView();
                mav.addObject("products", shoppingCardService.getProductsInCart());
                mav.addObject("total", shoppingCardService.getTotal());
                mav.setViewName("views/payment/paymentSelection");
                return mav;
        }else{
            return shoppingCart();
        }
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        shoppingCardService.checkout();
        return shoppingCart();
    }


}
