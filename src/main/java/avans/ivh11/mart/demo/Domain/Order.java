package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders2")
public class Order extends BaseOrder {

    private String status;

    private Calendar created = Calendar.getInstance();

    @Override
    public float price() {
        return 0;
    }
}
