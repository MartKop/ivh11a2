package avans.ivh11.mart.demo.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Order pending")
public class OrderPendingState extends OrderState {

    void orderPendingState(Order order){

    }
    boolean canCancel(Order order){
        return true;
    }

    boolean canShip(Order order){
        return false;
    }

}
