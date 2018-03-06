package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name is required.")
    @Size(min = 3, max = 50, message = "3 to 50 characters")
    private String name;

    @NotNull(message = "Description is required.")
    @Size(min = 3, max = 150, message = "3 to 150 characters")
    private String description;

    @NotNull(message = "Price is required.")
    private float price;

    private boolean active;

    private Calendar created = Calendar.getInstance();

    private String photo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @OneToMany(cascade=CascadeType.ALL, targetEntity=Review.class)
    @JoinColumn(name="id")
    private List<Review> reviews = new ArrayList<>();

}
