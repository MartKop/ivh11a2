package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderRow;
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

    /**
     * Get the total prize of an order by finding order-rows and adding their prices together
     *
     * @param order
     * @return
     */
    public Float getTotalPrice(Order order) {
        Float total = 0.0f;
        for (OrderRow prod : order.getProducts()) {
            total += prod.getProduct().getPrice() * prod.getQuantity();

        }
        return total;
    }

    /**
     * Change the order state to cancel an order
     *
     * @param order z
     */
    @Transactional
    public void cancelOrder(Order order) {
        orderStateRepository.delete(order.getOrderState().getId());
        order.cancelOrder();
    }
}

