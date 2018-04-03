package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Domain.Product;
import java.util.Map;


public interface ShoppingCartService {

    void addProduct(Product product);
    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout(BaseUser user);

    int getSize();

    Float getTotal();


    boolean isWrappingPaper();

    boolean isBow();

    void setWrappingPaper(boolean wrappingPaper);

    void setBow(boolean bow);
}
