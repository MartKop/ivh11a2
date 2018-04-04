package avans.ivh11.mart.demo.Service.BuilderPattern;

import avans.ivh11.mart.demo.Domain.BaseOrder;
import avans.ivh11.mart.demo.Domain.Order;
import avans.ivh11.mart.demo.Domain.OrderState.OrderPendingState;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewOrderBuilder extends OrderBuilder {
    @Override
    public Order buildOrder() {
        this.createNewOrder();
        this.order.setOrderState(new OrderPendingState(this.order));
        return this.getOrder();
    }
}
