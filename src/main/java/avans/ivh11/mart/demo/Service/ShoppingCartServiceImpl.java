package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.*;
import avans.ivh11.mart.demo.Domain.OrderState.OrderPendingState;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
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
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private BaseOrderRepository baseOrderRepository;

    @Autowired
    private BaseOrderRepository<OrderOption> orderOptionRepository;

    private Map<Product, Integer> products = new HashMap<>();

    private boolean wrappingPaper, bow;

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
    @Transactional
    public void checkout(BaseUser user) {
        List<OrderRow> orderRows = new LinkedList<>();
        Order order = new Order();
        for (Map.Entry<Product, Integer> pair : products.entrySet()) {
            OrderRow row = new OrderRow();
            row.setProduct(pair.getKey());
            row.setQuantity(pair.getValue());
            row.setOrder(order);
            orderRows.add(row);
        }
        order.setProducts(orderRows);
        order.setUser(user);
        order.setOrderState(new OrderPendingState(order));
        baseOrderRepository.save(order);
        if (bow) {
            OrderOption bow = new OrderOption(order, "Bow", 1.00F, user);
            orderOptionRepository.save(bow);
        }
        if (wrappingPaper) {
            OrderOption wrapping = new OrderOption(order, "Wrapping paper", 2.50F, user);
            orderOptionRepository.save(wrapping);
        }
        wrappingPaper = false;
        bow = false;
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
        if (wrappingPaper) {
            total += 2.50f;
        }
        if (bow) {
            total += 1.00f;
        }
        return total;
    }

    @Override
    public void updateQuantity(Product product, int quantity) {
            if(products.containsKey(product)){
                products.replace(product, quantity);
            }
    }

    public boolean isWrappingPaper() {
        return wrappingPaper;
    }

    public void setWrappingPaper(boolean wrappingPaper) {
        this.wrappingPaper = wrappingPaper;
    }

    public boolean isBow() {
        return bow;
    }

    public void setBow(boolean bow) {
        this.bow = bow;
    }
}
