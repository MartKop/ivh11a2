package avans.ivh11.mart.demo.Domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "category")
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
}
