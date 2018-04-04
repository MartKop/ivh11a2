package avans.ivh11.mart.demo.Service.Importer;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.LocalJsonAPIConnector;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Getter
@Setter
@Service
public class LocalJsonAPIProductAdapter implements ProductAdapter {

    private LocalJsonAPIConnector localJsonAPI;

    private ProductRepository productRepository;

    @Autowired
    public LocalJsonAPIProductAdapter(ProductRepository productRepository) {
        this.localJsonAPI = new LocalJsonAPIConnector();
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductList() {
        Iterable<Product> webshopProducts = this.productRepository.findAll();
        JSONArray products = localJsonAPI.getProducts();

        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < products.length(); i++) {
            JSONObject jsonProduct = products.getJSONObject(i);
            if (StreamSupport.stream(webshopProducts.spliterator(), false).noneMatch(x -> x.getName().equals(StringUtils.abbreviate(jsonProduct.getString("naam"), 500)))) {
                productList.add(this.createProduct(jsonProduct));
            }
        }

        return productList;
    }

    private Product createProduct(JSONObject jsonProduct) {
        Product product = new Product();

        product.setName(jsonProduct.getString("naam"));
        product.setPrice(jsonProduct.getFloat("prijs"));
        product.setActive(true);
        product.setDescription(jsonProduct.getString("beschrijving"));
        product.setPhoto(jsonProduct.getString("foto_url"));

        return product;
    }
}
