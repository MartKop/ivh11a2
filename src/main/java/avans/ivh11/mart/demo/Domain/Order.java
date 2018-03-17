package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseOrder {

    private String status;


    private Calendar created = Calendar.getInstance();



    public OrderState orderState;

    public OrderState getOrderState() {
        return orderState;
    }

    public boolean canCancel(){
        return orderState.canCancel(this);
    }

    public void cancelOrder(){
        if(orderState.canCancel(this)){
            orderState.cancelOrder(this);
        }
    }

    public void SentOrder(){
        if(orderState.canShip(this)){
            orderState.orderSentState(this);
        }
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Override
    public float price() {
        return 0;
    }
}
