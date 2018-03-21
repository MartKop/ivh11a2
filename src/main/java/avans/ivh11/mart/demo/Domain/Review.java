package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "Review")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Min(value = 0, message = "Maximum rating is 0")
    @Max(value = 10, message = "Maximum rating is 10")
    private int rating;

    @NotNull(message = "Comment is required.")
    private String comment;

    @ManyToOne
    @JoinColumn(name="registered_user_id", nullable=false)
    private RegisteredUser user;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
}
