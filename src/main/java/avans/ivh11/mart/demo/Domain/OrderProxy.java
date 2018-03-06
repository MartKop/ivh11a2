package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderProxy implements IOrder {
    
    @Override
    public Order getOrder() {
        return null;
    }
}
