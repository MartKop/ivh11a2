package avans.ivh11.mart.demo.Domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("unregistered")
public class UnregisteredUser extends BaseUser {
}
