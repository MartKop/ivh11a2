package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(appliesTo = "orders")
public abstract class Order extends BaseOrder implements IOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String status;

    private Calendar created = Calendar.getInstance();

    protected Payment payment;

    @Override
    public float price() {
        return 0;
    }

    public void applyPayment() {
        payment.pay();
    }
}
