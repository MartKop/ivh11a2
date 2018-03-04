package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import java.util.Calendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(appliesTo = "orders")
public class Order extends BaseOrder {

    private String status;

    private Calendar created = Calendar.getInstance();

    @Override
    public float price() {
        return 0;
    }
}
