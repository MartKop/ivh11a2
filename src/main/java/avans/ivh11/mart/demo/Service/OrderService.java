package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderRow;
import avans.ivh11.mart.demo.Domain.OrderState.OrderCancelledState;
import avans.ivh11.mart.demo.Repository.BaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private BaseOrderRepository baseOrderRepository;

    public Order getOrderDetails(Long id) {
        return (Order) baseOrderRepository.findOne(id);
    }

    /**
     * Get the total prize of an order by finding order-rows and adding their prices together
     *
     * @param order
     * @return
     */
    public Float getTotalPrice(Order order) {
        Float total = 0.0f;
        for (OrderRow prod : order.getProducts()) {
            total += prod.getProduct().getPrice();
        }
        return total;
    }

    /**
     * Change the order state to cancel an order
     *
     * @param order
     */
    public void cancelOrder(Order order) {
        order.setOrderState(new OrderCancelledState(order));
        baseOrderRepository.save(order);
    }
}
