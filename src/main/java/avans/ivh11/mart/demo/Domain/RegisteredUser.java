package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue("registered")
public class RegisteredUser extends BaseUser {

    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Column(nullable = true, unique = true)
    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy= "user")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy= "user")
    private List<Review> reviews = new ArrayList<>();

    private String role;
}
