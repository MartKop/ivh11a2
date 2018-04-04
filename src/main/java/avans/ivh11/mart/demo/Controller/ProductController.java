package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.*;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FlashService flashService;

    public ProductController() {

    }

    @GetMapping
    @RequestMapping("/product")
    public ModelAndView list() {
        Iterable<Product> products = this.productRepository.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "overzicht Producten");
        mav.addObject("products", products);
        mav.setViewName("views/product/list");
        return mav;
    }

    @GetMapping
    @RequestMapping("/productOverview")
    public ModelAndView listOverview() {
        Iterable<Product> products = this.productRepository.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Product - Lijst");
        mav.addObject("products", products);
        mav.setViewName("views/product/productOverview");
        return mav;
    }

    @GetMapping(value = "/product/{id}")
    public ModelAndView view(@PathVariable("id") String productId) {
        Product product = this.productRepository.findOne(Long.parseLong(productId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Product - " + productId);
        mav.addObject("product", product);
        mav.addObject("review", new Review());
        mav.setViewName("views/product/view");
        return mav;
    }


    @DeleteMapping(value = "{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.productRepository.delete(id);

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "product - List");
        mav.addObject("products", this.productRepository.findAll());
        mav.addObject("flash", this.flashService.createFlash("success", "Product " + id + " was verwijderd."));
        mav.setViewName("redirect:/product/");

        return mav;
    }
}
