package avans.ivh11.mart.demo.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Setter
@NoArgsConstructor
@Getter
@Table(name = "user")
@Inheritance
@DiscriminatorColumn(name = "user_type")
@Entity
public class BaseUser implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "Email is required.")
	@Email(message = "Email is invalid")
	private String email;

	@NotEmpty(message = "First name is required.")
	@Size(min = 3, max = 25, message = "3 to 25 characters")
	private String firstName;

	private String infix;

	@NotEmpty(message = "Last name is required.")
	@Size(min = 3, max = 25, message = "3 to 25 characters")
	private String lastName;

	@Pattern(regexp = "^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$", message = "Format: DD-MM-YYYY Required")
	@NotEmpty(message = "Date of birth is required.")
	private String dateOfBirth;

	@Size(min = 10, max = 15)
	private String phone;


	@OneToMany(mappedBy= "user")
	private Set<Order> orders = new HashSet<>();

	private Calendar created = Calendar.getInstance();

	private boolean subscribeToNewsletter;

	@Transient
	private String fullName;

	public String getFullName() {
		if(this.infix.isEmpty())
			return this.firstName + " " + this.lastName;

		return this.firstName + " " + this.infix + " " + this.lastName;
	}

	public void updateBaseUser(BaseUser user) {
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.infix = user.getInfix();
		this.lastName = user.getLastName();
		this.dateOfBirth = user.getDateOfBirth();
		this.phone = user.getPhone();
		this.subscribeToNewsletter = user.isSubscribeToNewsletter();
	}
}


