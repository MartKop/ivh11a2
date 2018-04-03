package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderRow;
import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import avans.ivh11.mart.demo.Repository.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private BaseOrderRepository baseOrderRepository;

    @Autowired
    private OrderStateRepository orderStateRepository;

    public Order getOrderDetails(Long id) {
        return (Order) baseOrderRepository.findOne(id);
    }

    public Float getTotalPrice(Order order) {
        Float total = 0.0f;
        for (OrderRow prod : order.getProducts()) {
            total += prod.getProduct().getPrice() * prod.getQuantity();
        }
        return total;
    }

    @Transactional
    public void cancelOrder(Order order) {
        orderStateRepository.delete(order.getOrderState().getId());
        order.cancelOrder();
        baseOrderRepository.save(order);
    }


}
