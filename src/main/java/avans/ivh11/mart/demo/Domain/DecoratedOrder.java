package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
public class DecoratedOrder extends BaseOrder {

    @OneToOne
    protected BaseOrder baseOrder;

    protected DecoratedOrder(BaseOrder baseOrder) {
        this.baseOrder = baseOrder;
    }

    @Override
    public float price() {
        return 0;
    }


}
