package avans.ivh11.mart.demo.Model;

import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Table(appliesTo = "user")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "First name is required.")
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

	private Calendar created = Calendar.getInstance();

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getInfix() {
		return infix;
	}

	public void setInfix(String infix) {
		this.infix = infix;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}
}
