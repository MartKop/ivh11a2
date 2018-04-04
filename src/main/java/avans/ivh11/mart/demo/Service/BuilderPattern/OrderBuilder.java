package avans.ivh11.mart.demo.Service.BuilderPattern;

import avans.ivh11.mart.demo.Domain.Order;

public abstract class OrderBuilder {
    protected Order order;

    public Order getOrder() {
        return order;
    }

    public void createNewOrder() {
        this.order = new Order();
    }

    public abstract Order buildOrder();
}

