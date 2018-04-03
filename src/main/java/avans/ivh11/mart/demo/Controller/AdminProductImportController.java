package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.Importer.BolAPIProductAdapter;
import avans.ivh11.mart.demo.Service.Importer.LocalJsonAPIProductAdapter;
import avans.ivh11.mart.demo.Service.Importer.ProductAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin/products/add/")
public class AdminProductImportController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FlashService flashService;

    @GetMapping(value = "local")
    public ModelAndView addLocalProducts(RedirectAttributes redirect) {
        ProductAdapter localJsonAPIProductAdapter = new LocalJsonAPIProductAdapter(productRepository);

        List<Product> products = localJsonAPIProductAdapter.getProductList();
        HashMap<String, String> flash = this.saveProductsAndFlash(products);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/welcome");
        redirect.addFlashAttribute("flash", flash);
        return mav;
    }

    @GetMapping(value = "bol")
    public ModelAndView addBolProducts(RedirectAttributes redirect) {
        ProductAdapter bolAPIProductAdapter = new BolAPIProductAdapter(productRepository);

        List<Product> products = bolAPIProductAdapter.getProductList();
        HashMap<String, String> flash = this.saveProductsAndFlash(products);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/welcome");
        redirect.addFlashAttribute("flash", flash);
        return mav;
    }

    private HashMap<String, String> saveProductsAndFlash(List<Product> products) {
        if (products.size() > 0) {
            this.productRepository.save(products);
            return this.flashService.createFlash("success", "Er zijn " + products.size() + " producten aangemaakt.");
        } else {
            return this.flashService.createFlash("warning", "0 producten zijn aangemaakt.");
        }
    }
}
