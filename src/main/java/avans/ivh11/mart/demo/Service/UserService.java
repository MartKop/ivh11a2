package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private BaseUserRepository<RegisteredUser> registeredUserRepository;



    public boolean validateUser(RegisteredUser user, BindingResult bindingResult) {
        if (user.getUsername().length() <= 6 || user.getUsername().length() >= 32) {
            bindingResult.addError(new FieldError("user", "username", "Size must be 6 to 32 characters"));
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }

    public boolean save(RegisteredUser registeredUser) {

    }

    public RegisteredUser getUserById(Long id) {

    }

    public RegisteredUser deleteUserById(Long id) {

    }

    public Iterable<RegisteredUser> findAll() {
        return this.registeredUserRepository.findAll();
    }

    public RegisteredUser getUserByCriteria(Long id) {

    }
}
