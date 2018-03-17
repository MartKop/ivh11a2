package avans.ivh11.mart.demo.Domain;


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


    boolean canCancel(Order order){
        return false;
    }

    boolean canShip(Order order){
        return false;
    }
}
