package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;

@Controller
@RequestMapping("/product")
public class ProductController {

//    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public ProductController() {
//
//    }
//
//    @GetMapping
//    public ModelAndView list() {
//        Iterable<Product> products = this.productRepository.findAll();
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("title", "Product - List");
//        mav.addObject("products", products);
//        mav.setViewName("views/product/list");
//
//        return mav;
//    }
//
//    @GetMapping(value = "{id}")
//    public ModelAndView view(@PathVariable("id") String productId) {
//        Product product = this.productRepository.findOne(Long.parseLong(productId));
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("title", "Product - " + productId);
//        mav.addObject("product", product);
//        mav.setViewName("views/product/view");
//
//        return mav;
//    }
//
//    @GetMapping(value = "create")
//    public ModelAndView createForm(@ModelAttribute Product product) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("title", "product - Create");
//        mav.setViewName("views/product/form");
//
//        return mav;
//    }
//
//    @PostMapping(value = "create")
//    public ModelAndView create(@Valid Product product, BindingResult result, RedirectAttributes redirect) {
//        ModelAndView mav = new ModelAndView();
//
//        if (result.hasErrors()) {
//            mav.addObject("title", "Product - Create");
//            mav.addObject("form_errors", result.getAllErrors());
//            mav.setViewName("views/product/form");
//
//            return mav;
//        }
//
//        try {
//            if(result.hasFieldErrors("price")) {
//                product = this.checkProductConstraints(product, result);
//            }
//            product = this.productRepository.save(product);
//        } catch (ConstraintViolationException e) {
//            throw e;
//        } catch(Exception e) {
//            throw e;
//        }
//
//        mav.addObject("product.id", product.getId());
//        mav.setViewName("redirect:/product/{product.id}");
//        redirect.addFlashAttribute("flash", this.createFlash("success", "Successfully created a new product"));
//
//        return mav;
//    }
//
//    @GetMapping(value = "{id}/edit")
//    public ModelAndView modifyProductForm(@PathVariable("id") String productId) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("title", "Product - Edit");
//        mav.addObject("product", this.productRepository.findOne(Long.parseLong(productId)));
//        mav.addObject("edit", true);
//        mav.setViewName("views/product/form");
//
//        return mav;
//    }
//
//    @PostMapping(value = "{id}/edit")
//    public ModelAndView updateProduct(@Valid Product product, BindingResult result, RedirectAttributes redirect) {
//        ModelAndView mav = new ModelAndView();
//
//        if (result.hasErrors()) {
//            mav.addObject("title", "product - Edit");
//            mav.addObject("form_errors", result.getAllErrors());
//            mav.addObject("edit", true);
//            mav.setViewName("views/product/form");
//
//            return mav;
//        }
//
//        product = this.productRepository.save(product);
//        mav.addObject("product.id", product.getId());
//        mav.setViewName("redirect:/product/{product.id}");
//        redirect.addFlashAttribute("flash", this.createFlash("success", "Successfully updated product " + product.getId()));
//
//        return mav;
//    }
//
//    @DeleteMapping(value = "{id}/delete")
//    public ModelAndView delete(@PathVariable("id") Long id) {
//        this.productRepository.delete(id);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("title", "product - List");
//        mav.addObject("products", this.productRepository.findAll());
//        mav.addObject("flash", this.createFlash("success", "Succesfully deleted product" + id));
//        mav.setViewName("redirect:/product/");
//
//        return mav;
//    }
//
//    private HashMap<String, String> createFlash(String type, String text)
//    {
//        HashMap<String, String> flash = new HashMap<>();
//        flash.put("type", type);
//        flash.put("text", text);
//
//        return flash;
//    }
//
//    private Product checkProductConstraints(Product product, BindingResult result)
//    {
//        final String priceRegex = "[0-9]+([,.][0-9]{1,2})?";
//        if (!result.getFieldValue("price").toString().matches(priceRegex)) {
//            result.addError(new ObjectError("price_formatter", "Price must have format 10.00 or 10,00"));
//            return product;
//        }
//
//        try {
//            product.setPrice(Float.parseFloat(result.getFieldValue("price").toString().replace(",", ".")));
//        } catch (NumberFormatException e) {
//            result.addError(new ObjectError("price_converter", "Could not convert string to bigdecimal"));
//            return product;
//        }
//        catch (Exception e) {
//            result.addError(new ObjectError("price_setter", "Error when settings price"));
//            return product;
//        }
//
//        return product;
//    }
}