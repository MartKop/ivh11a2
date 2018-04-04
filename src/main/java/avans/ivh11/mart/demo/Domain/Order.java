package avans.ivh11.mart.demo.Domain;

import avans.ivh11.mart.demo.Domain.OrderState.OrderSentState;
import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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
    private OrderState orderState;

    private Float price;

    public Order(String status, BaseUser user, Calendar created, List<OrderRow> products, OrderState orderState) {
        this.status = status;
        this.user = user;
        this.created = created;
        this.products = products;
        this.orderState = orderState;
        loadPrice();
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public boolean canCancel() {
        return orderState.canCancel(this);
    }

    public void cancelOrder() {
        if (orderState.canCancel(this)) {
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

    private void loadPrice(){
        Float price = 0.0f;
        for (OrderRow product : products) {
            price += product.getProduct().getPrice() * product.getQuantity();
        }
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public float price() {
        this.loadPrice();
        return this.price;
    }
}