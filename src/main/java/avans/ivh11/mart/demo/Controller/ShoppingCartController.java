package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.ShoppingCardService;
import avans.ivh11.mart.demo.Service.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCardService shoppingCardService;


    public ShoppingCartController(){
    }


    @GetMapping("/shoppingcart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("shoppingCart");
        modelAndView.addObject("products", shoppingCardService.getProductsInCart());
        modelAndView.addObject("total", shoppingCardService.getTotal().toString());
        modelAndView.setViewName("views/shoppingcart/shoppingCart");
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        shoppingCardService.addProduct(productRepository.findOne(productId));
        ModelAndView modelAndView = new ModelAndView("shoppingCart");
        return modelAndView;
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        shoppingCardService.removeProduct(productRepository.findOne(productId));
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        shoppingCardService.checkout();
        return shoppingCart();
    }


}
