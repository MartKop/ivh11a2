package avans.ivh11.mart.demo.Domain.OrderState;

import avans.ivh11.mart.demo.Domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@NoArgsConstructor
@Getter
@Table(name = "orders_state")
@DiscriminatorColumn(name = "state")
@Inheritance
@Entity(name = "order_state")
public abstract class OrderState implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public OrderState(Order order) {
        this.order = order;
    }

    public boolean canCancel(Order order) {
        return false;
    }

    public boolean canShip(Order order) {
        return false;
    }


    public void cancelOrder(Order order) {
        order.setOrderState(new OrderCancelledState(order));
    }

    @Override
    public String toString() {
        return "OrderState";
    }
}
