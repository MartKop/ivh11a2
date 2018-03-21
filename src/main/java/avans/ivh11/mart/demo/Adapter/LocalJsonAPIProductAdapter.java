package avans.ivh11.mart.demo.Adapter;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.LocalJsonAPIConnector;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LocalJsonAPIProductAdapter implements ProductAdapter {

    private LocalJsonAPIConnector localJsonAPI;

    public LocalJsonAPIProductAdapter() {
        this.localJsonAPI = new LocalJsonAPIConnector();
    }

    @Override
    public List<Product> getProductList()
    {
        JSONArray products = localJsonAPI.getProducts();

        List<Product> productList = new ArrayList<>();

        for(int i = 0; i < products.length(); i++){
            productList.add(this.createProduct(products.getJSONObject(i)));
        }

        return productList;
    }

    private Product createProduct(JSONObject jsonProduct)
    {
        Product product = new Product();

        product.setName(jsonProduct.getString("naam"));
        product.setPrice(jsonProduct.getFloat("prijs"));
        product.setActive(true);
        product.setDescription(jsonProduct.getString("beschrijving"));
        product.setPhoto(jsonProduct.getString("foto_url"));

        return product;
    }
}
