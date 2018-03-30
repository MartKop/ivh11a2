package avans.ivh11.mart.demo.Domain;

import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "order")
public class Order extends BaseOrder implements IOrder {

    private String status;


    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private BaseUser user;

    private Calendar created = Calendar.getInstance();

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "order")
    private List<OrderRow> products = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = true)
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
    public Order getOrder() {
        return null;
    }

    @Override
    public float price() {
        return 0;
    }
}