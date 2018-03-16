package avans.ivh11.mart.demo.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue("Order Empty")
public class OrderEmptyState extends OrderState {

}
