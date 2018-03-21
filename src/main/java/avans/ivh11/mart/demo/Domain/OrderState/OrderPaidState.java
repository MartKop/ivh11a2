package avans.ivh11.mart.demo.Domain.OrderState;

import avans.ivh11.mart.demo.Domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue("paid")
public class OrderPaidState extends OrderState {

    public boolean canCancel(Order order){
        return true;
    }

    public boolean canShip(Order order){
        return true;
    }
}
