package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ShoppingCartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("views/shoppingCart/shoppingCart");
        modelAndView.addObject("title", "Winkelwagen");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        modelAndView.addObject("wrapping", shoppingCartService.isWrappingPaper());
        modelAndView.addObject("bow", shoppingCartService.isBow());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        shoppingCartService.addProduct(productRepository.findOne(productId));
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        shoppingCartService.removeProduct(productRepository.findOne(productId));
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/goToPayment")
    public ModelAndView goToPayment() {
        if (shoppingCartService.getSize() > 0) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("products", shoppingCartService.getProductsInCart());
            mav.addObject("total", shoppingCartService.getTotal());
            mav.setViewName("views/payment/paymentSelection");
            return mav;
        } else {
            return shoppingCart();
        }
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout(@ModelAttribute("user") BaseUser unregUser) {
        if (unregUser.getEmail() != null) {
            shoppingCartService.checkout(unregUser);
            ModelAndView mav = new ModelAndView("views/product/productOverview");
            return mav;
        } else {
            BaseUser user = (BaseUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            shoppingCartService.checkout(user);
            ModelAndView mav = new ModelAndView("views/product/productOverview");
            return mav;
        }
    }


    @GetMapping("/shoppingCart/bow")
    @ResponseBody
    public Float updateBow(@RequestParam boolean checked) {
        shoppingCartService.setBow(checked);
        return shoppingCartService.getTotal();
    }

    @GetMapping("/shoppingCart/wrapping")
    @ResponseBody
    public Float updateWrapping(@RequestParam boolean checked) {
        shoppingCartService.setWrappingPaper(checked);
        return shoppingCartService.getTotal();
    }


}
