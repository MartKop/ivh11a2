package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Adapter.BolAPIProductAdapter;
import avans.ivh11.mart.demo.Adapter.LocalJsonAPIProductAdapter;
import avans.ivh11.mart.demo.Adapter.ProductAdapter;
import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ProductRepository productRepository;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @GetMapping(value = "showProducts")
    public ModelAndView modifyProductForm() {
        ProductAdapter bolAPIProductAdapter = new BolAPIProductAdapter(productRepository);
        ProductAdapter localJsonAPIProductAdapter = new LocalJsonAPIProductAdapter(productRepository);

        List<Product> products = bolAPIProductAdapter.getProductList();
        products.addAll(localJsonAPIProductAdapter.getProductList());

        productRepository.save(products);

        ModelAndView mav = new ModelAndView();
        mav.addObject("products", products);
        mav.setViewName("views/test/products");
        return mav;
    }
}
