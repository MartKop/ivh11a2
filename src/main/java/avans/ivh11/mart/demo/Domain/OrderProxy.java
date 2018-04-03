package avans.ivh11.mart.demo.Domain;

import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
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
