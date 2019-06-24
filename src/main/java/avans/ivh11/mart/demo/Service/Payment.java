package avans.ivh11.mart.demo.Service;

public abstract class Payment implements IPaymentStrategy {
    public abstract String pay(String price);
}
