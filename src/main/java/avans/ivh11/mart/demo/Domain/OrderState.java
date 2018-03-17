package avans.ivh11.mart.demo.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@NoArgsConstructor
@Getter
@Table(name = "Order_State")
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

     boolean canCancel(Order order){
        return false;
     }

     boolean canShip(Order order){
        return false;
     }
    void orderPaidState(Order order){

    }

    void orderSentState(Order order){

    }

    void cancelOrder(Order order){

    }

}
