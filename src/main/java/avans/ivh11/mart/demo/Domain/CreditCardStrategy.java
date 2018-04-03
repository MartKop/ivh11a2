package avans.ivh11.mart.demo.Domain;

public class CreditCardStrategy implements PaymentStrategy {

    private final String nameOnCard;
    private final String number;
    private final String cvv;
    private final String expirationDate;

    public CreditCardStrategy(String nameOnCard, String number, String cvv, String expirationDate) {
        this.nameOnCard = nameOnCard;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    @Override
    public String pay() {
        System.out.println(" paid with creditcard.");
        return "/";
    }
}
