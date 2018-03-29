package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderOption;
import avans.ivh11.mart.demo.Domain.OrderRow;
import avans.ivh11.mart.demo.Domain.OrderState.OrderPendingState;
import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BaseOrderRepository baseOrderRepository;

    @Autowired
    private  BaseOrderRepository<OrderOption> orderOptionRepository;

    private Map<Product, Integer> products = new HashMap<>();

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
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

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }


    @Override
    public void checkout() {
        List<OrderRow> orderRows = new LinkedList<>();
        for(Map.Entry<Product, Integer> pair : products.entrySet()) {
            OrderRow row = new OrderRow();
            row.setProduct(pair.getKey());
            row.setQuantity(pair.getValue());
            orderRows.add(row);
        }
        Order order = new Order();
        order.setProducts(orderRows);
        order.setOrderState(new OrderPendingState(order));

        products.clear();
    }

    @Override
    public int getSize() {
        return products.size();
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
