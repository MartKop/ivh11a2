package avans.ivh11.mart.demo.Domain;

import avans.ivh11.mart.demo.Domain.OrderState.OrderSentState;
import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "order")
public class Order extends BaseOrder {

    private String status;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private BaseUser user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
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
            this.setOrderState(new OrderSentState(this));
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
