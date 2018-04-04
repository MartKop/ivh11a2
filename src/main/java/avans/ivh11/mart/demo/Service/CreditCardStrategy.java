package avans.ivh11.mart.demo.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class CreditCardStrategy extends Payment {

     @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nameOnCard;
    private String number;
    private String cvv;
    private String expirationDate;

    public CreditCardStrategy(String nameOnCard, String number, String cvv, String expirationDate) {
        this.nameOnCard = nameOnCard;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    @Override
    public String pay(String amount) {
        System.out.println(amount + " paid with creditcard.");
        return "/";
    }
}
