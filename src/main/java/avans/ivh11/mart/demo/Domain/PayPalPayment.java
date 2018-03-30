package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PayPalPayment implements IPayment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Override
    public void pay() {
    }
}
