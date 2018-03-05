package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@NoArgsConstructor
@Getter
@Entity(name = "registeredUser")
@Table(appliesTo = "registered_user")
public class RegisteredUser extends BaseUser {

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String passwordConfirm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy= "user")
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}