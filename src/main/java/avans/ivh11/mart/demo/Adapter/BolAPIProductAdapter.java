package avans.ivh11.mart.demo.Adapter;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.BolAPIConnector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BolAPIProductAdapter implements ProductAdapter {

    private BolAPIConnector bolAPI;

    public BolAPIProductAdapter() {
        this.bolAPI = new BolAPIConnector();
    }

    @Override
    public List<Product> getProductList() {
        return this.getComputerCategoryProducts();
    }

    public List<Product> getComputerCategoryProducts() {
        JSONArray products = bolAPI.getComputerCategoryProducts();

        List<Product> productList = new ArrayList<>();

        for(int i = 0; i < products.length(); i++){
            productList.add(this.createProduct(products.getJSONObject(i)));
        }

        return productList;
    }

    private Product createProduct(JSONObject jsonProduct)
    {
        Product product = new Product();

        product.setId(jsonProduct.getLong("id"));
        product.setName(jsonProduct.getString("title"));
        product.setPrice(jsonProduct.getJSONObject("offerData").getJSONArray("offers").getJSONObject(0).getFloat("price"));
        product.setActive(true);
        product.setDescription(jsonProduct.getString("longDescription"));
        product.setPhoto(jsonProduct.getJSONArray("images").getJSONObject(3).getString("url"));

        return product;
    }
}
