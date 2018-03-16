package avans.ivh11.mart.demo.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@NoArgsConstructor
@Getter
@Table(name = "orders2")
@Inheritance
@Entity
@DiscriminatorColumn(name = "OrderState")
public abstract class OrderState implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

     void orderEmptyState(Order order){

     }

     void orderPendingState(Order order){

     }


    void orderPaidState(Order order){

    }

    void orderSentState(Order order){

    }

}
