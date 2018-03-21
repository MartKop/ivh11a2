package avans.ivh11.mart.demo.Domain.OrderState;


import avans.ivh11.mart.demo.Domain.Order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("pending")
public class OrderPendingState extends OrderState {

    public void orderPendingState(Order order){

    }

    public boolean canCancel(Order order){
        return true;
    }

    public boolean canShip(Order order){
        return false;
    }

}
