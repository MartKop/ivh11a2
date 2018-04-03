package avans.ivh11.mart.demo.Service.Adapter;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.BolAPIConnector;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Getter
@Setter
@Service
public class BolAPIProductAdapter implements ProductAdapter {

    private BolAPIConnector bolAPI;

    private ProductRepository productRepository;

    @Autowired
    public BolAPIProductAdapter(ProductRepository productRepository) {
        this.bolAPI = new BolAPIConnector();
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductList() {
        return this.getComputerCategoryProducts();
    }

    public List<Product> getComputerCategoryProducts() {
        Iterable<Product> webshopProducts = this.productRepository.findAll();
        JSONArray products = bolAPI.getComputerCategoryProducts();

        List<Product> productList = new ArrayList<>();

        for(int i = 0; i < products.length(); i++){
            JSONObject jsonProduct = products.getJSONObject(i);
            if (StreamSupport.stream(webshopProducts.spliterator(), false).noneMatch(x -> x.getName().equals(StringUtils.abbreviate(jsonProduct.getString("title"), 50)))) {
                productList.add(this.createProduct(jsonProduct));
            }
        }

        return productList;
    }

    private Product createProduct(JSONObject jsonProduct)
    {
        Product product = new Product();

        product.setId(jsonProduct.getLong("id"));
        product.setName(StringUtils.abbreviate(jsonProduct.getString("title"), 50));
        product.setPrice(jsonProduct.getJSONObject("offerData").getJSONArray("offers").getJSONObject(0).getFloat("price"));
        product.setActive(true);
        product.setDescription(StringUtils.abbreviate(jsonProduct.getString("longDescription"), 150));
        product.setPhoto(jsonProduct.getJSONArray("images").getJSONObject(3).getString("url"));

        return product;
    }
}
