package avans.ivh11.mart.demo.Domain;

import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "order")
public class Order extends BaseOrder {

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY, optional = true)
    public OrderState orderState;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BaseUser user;
    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "order")
    private List<OrderRow> products = new ArrayList<>();

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public boolean canCancel() {
        return orderState.canCancel(this);
    }

    public void cancelOrder() {
        if (orderState.canCancel(this)) {
            orderState.cancelOrder(this);
        }
    }

    public void SentOrder() {
        if (orderState.canShip(this)) {
            orderState.orderSentState(this);
        }
    }

    @Override
    public float price() {
        return 0;
    }
}
