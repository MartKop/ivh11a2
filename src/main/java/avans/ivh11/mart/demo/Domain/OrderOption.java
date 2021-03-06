package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "order_option")
public class OrderOption extends DecoratedOrder {

    private String name;

    private float price;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)

    private BaseUser user;

    public OrderOption(BaseOrder order, String name, float price, BaseUser user) {
        super(order);
        this.name = name;
        this.price = price;
        this.user = user;
    }

    @Override
    public float price() {
        return price;
    }

    @Override
    public String toString() {
        return "Option: " + name + "; " + order;
    }
}
