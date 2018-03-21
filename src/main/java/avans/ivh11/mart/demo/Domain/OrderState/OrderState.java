package avans.ivh11.mart.demo.Domain.OrderState;


import avans.ivh11.mart.demo.Domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@NoArgsConstructor
@Getter
@Table(name = "order_state")
@Inheritance
@Entity
public abstract class OrderState implements Serializable {

    private Order order;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public OrderState(Order order) {
        this.order = order;
    }

    void orderEmptyState(Order order){

     }

     void orderPendingState(Order order){

     }

    public boolean canCancel(Order order){
return false;
}

    public boolean canShip(Order order){
return false;
}

    public void orderPaidState(Order order){

    }

    public void orderSentState(Order order){

    }

    public void cancelOrder(Order order){

    }

}
