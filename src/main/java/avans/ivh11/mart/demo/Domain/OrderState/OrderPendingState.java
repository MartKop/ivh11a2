package avans.ivh11.mart.demo.Domain.OrderState;


import avans.ivh11.mart.demo.Domain.Order;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@NoArgsConstructor
@DiscriminatorValue("pending")
public class OrderPendingState extends OrderState {

    public OrderPendingState(Order order) {
        super(order);
    }

    public boolean canCancel(Order order){
        return true;
    }

    public boolean canShip(Order order){
        return false;
    }

}
