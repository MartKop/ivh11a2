package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderRow;
import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    BaseOrderRepository baseOrderRepository;

    public Order getOrderDetails(Long id){
        return (Order) baseOrderRepository.findOne(id);
    }

    public Float getTotalPrice(Order order){
        Float total = 0.0f;
        for(OrderRow prod : order.getProducts()){
            total += prod.getProduct().getPrice();
        }
        return total;
    }


}
