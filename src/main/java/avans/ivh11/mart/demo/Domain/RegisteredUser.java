package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy= "user")
    private List<Review> reviews = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;

    private String role;


}
