package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Map;


public interface ShoppingCardService {

    void addProduct(Product product);
    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout();

    int getSize();

    Float getTotal();
}
