package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.*;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BaseOrderRepository baseOrderRepository;

    @Autowired
    private  BaseOrderRepository<OrderOption> orderOptionRepository;
    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private FlashService flashService;

    public ProductController() {

    }

    @GetMapping
    @RequestMapping("/dummydata")
    public ModelAndView AddData(){
        Product producttest = new Product();
        producttest.setName("RAM-Geheugen");
        producttest.setDescription("Ram geheugen 8GB");
        producttest.setActive(true);
        producttest.setPrice(90);
        producttest.setQuantity(20);
        productRepository.save(producttest);
        Product producttest1 = new Product();
        producttest1.setName("Voeding");
        producttest1.setDescription("Voeding voor je computer");
        producttest1.setActive(true);
        producttest1.setPrice(85);
        producttest1.setQuantity(20);
        productRepository.save(producttest1);
        Product producttest2 = new Product();
        producttest2.setName("Moederbord");
        producttest2.setDescription("Moederbord LA2055");
        producttest2.setActive(true);
        producttest2.setPrice(125);
        producttest2.setQuantity(20);
        productRepository.save(producttest2);
        Product producttest3 = new Product();
        producttest3.setName("Processor");
        producttest3.setDescription("Intel i7");
        producttest3.setActive(true);
        producttest3.setPrice(125);
        producttest3.setQuantity(20);
        productRepository.save(producttest3);

        List<Product> orderItems = new ArrayList<>();
        orderItems.add(producttest);
        orderItems.add(producttest1);
        Order order = new Order();
        order.setOrderItems(orderItems);

        baseOrderRepository.save(order);

        Optional<Order> concreteOrder  = Optional.ofNullable(order);
        OrderOption decoratedOrder1 = new OrderOption(concreteOrder.get(),"Wrapping paper", 2.50F );
        orderOptionRepository.save(decoratedOrder1);
        ModelAndView mav = new ModelAndView("views/product/productOverview");
        return mav;
    }

    @GetMapping
    @RequestMapping("/product")
    public ModelAndView list() {
        Iterable<Product> products = this.productRepository.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Product - List");
        mav.addObject("products", products);
        mav.setViewName("views/product/list");
        return mav;
    }

    @GetMapping
    @RequestMapping("/productOverview")
    public ModelAndView listOverview() {
        Iterable<Product> products = this.productRepository.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Product - List");
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

    @GetMapping(value = "/product/create")
    public ModelAndView createForm(@ModelAttribute Product product) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "product - Create");
        mav.setViewName("views/product/form");

        return mav;
    }

    @PostMapping(value = "create")
    public ModelAndView create(@Valid Product product, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "Product - Create");
            mav.addObject("form_errors", result.getAllErrors());
            mav.setViewName("views/product/form");

            return mav;
        }

        try {
            if(result.hasFieldErrors("price")) {
                product = this.checkProductConstraints(product, result);
            }
            product = this.productRepository.save(product);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch(Exception e) {
            throw e;
        }

        mav.addObject("product.id", product.getId());
        mav.setViewName("redirect:/product/{product.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully created a new product"));

        return mav;
    }


    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyProductForm(@PathVariable("id") String productId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Product - Edit");
        mav.addObject("product", this.productRepository.findOne(Long.parseLong(productId)));
        mav.addObject("edit", true);
        mav.setViewName("views/product/form");

        return mav;
    }

    @PostMapping(value = "{id}/edit")
    public ModelAndView updateProduct(@Valid Product product, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "product - Edit");
            mav.addObject("form_errors", result.getAllErrors());
            mav.addObject("edit", true);
            mav.setViewName("views/product/form");

            return mav;
        }

        product = this.productRepository.save(product);
        mav.addObject("product.id", product.getId());
        mav.setViewName("redirect:/product/{product.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully updated product " + product.getId()));

        return mav;
    }

    @DeleteMapping(value = "{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.productRepository.delete(id);

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "product - List");
        mav.addObject("products", this.productRepository.findAll());
        mav.addObject("flash", this.flashService.createFlash("success", "Succesfully deleted product" + id));
        mav.setViewName("redirect:/product/");

        return mav;
    }

    private Product checkProductConstraints(Product product, BindingResult result)
    {
        final String priceRegex = "[0-9]+([,.][0-9]{1,2})?";
        if (!result.getFieldValue("price").toString().matches(priceRegex)) {
            result.addError(new ObjectError("price_formatter", "Price must have format 10.00 or 10,00"));
            return product;
        }

        try {
            product.setPrice(Float.parseFloat(result.getFieldValue("price").toString().replace(",", ".")));
        } catch (NumberFormatException e) {
            result.addError(new ObjectError("price_converter", "Could not convert string to bigdecimal"));
            return product;
        }
        catch (Exception e) {
            result.addError(new ObjectError("price_setter", "Error when settings price"));
            return product;
        }

        return product;
    }
}
