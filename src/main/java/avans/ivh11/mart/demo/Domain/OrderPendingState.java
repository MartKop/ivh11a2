package avans.ivh11.mart.demo.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Order pending")
public class OrderPendingState extends OrderState {

}
