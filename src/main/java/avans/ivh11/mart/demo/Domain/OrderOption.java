package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderOption extends DecoratedOrder {

    private String name;
    private float price;

    public OrderOption(BaseOrder order, String name, float price) {
        super(order);
        this.name = name;
        this.price = price;
    }

    @Override
    public float price() {
        return price;
    }

    @Override
    public String toString() {
        return "Option: " + name +"; " + order;
    }
}
