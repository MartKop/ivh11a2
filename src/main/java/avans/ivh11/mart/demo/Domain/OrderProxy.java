package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderProxy implements IOrder {

    private Order order = null;

    public Float getPrice(){
        if(order == null){
            order = new Order();
        }
        return order.getPrice();
    }
}
