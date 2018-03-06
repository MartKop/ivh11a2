package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderOption extends DecoratedOrder {

    public OrderOption(String name, int price, BaseOrder order) {
        super(order);
        this.name = name;
        this.price = price;
    }

    @NotEmpty(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Price cannot be 0")
    private float price;


}
