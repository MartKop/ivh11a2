package avans.ivh11.mart.demo.Domain.OrderState;


import avans.ivh11.mart.demo.Domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue("Order sent")
public class OrderSentState extends OrderState {
    public boolean canCancel(Order order){
        return false;
    }

    public boolean canShip(Order order){
        return false;
    }
}
