package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Transactional
@NoArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCardService {

    private Map<Product, Integer> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    @Override
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public void checkout() {
        products.clear();
    }

    @Override
    public Float getTotal() {
        Float total = 0.0f;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            total += entry.getKey().getPrice() * (new Float(entry.getValue()));
        }
        return total;
    }
}
