package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class DecoratedOrder extends BaseOrder {

    @OneToOne
    protected BaseOrder order;

    protected DecoratedOrder(BaseOrder order) {
        this.order = order;
    }

}
