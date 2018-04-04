package avans.ivh11.mart.demo.Service;

public abstract class Payment implements PaymentStrategy {
    public abstract String pay(String price);
}
