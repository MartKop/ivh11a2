package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import com.google.common.collect.Sets;
import static com.google.common.collect.MoreCollectors.onlyElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private BaseUserRepository<RegisteredUser> registeredUserRepository;

    private Set<RegisteredUser> list;

    public BindingResult validateUser(RegisteredUser user, BindingResult bindingResult) {
        if (user.getUsername().length() <= 6 || user.getUsername().length() >= 32) {
            bindingResult.addError(
                new FieldError(
                    "user",
                    "username",
                    "Size must be 6 to 32 characters"
                )
            );
        }

        if (this.getUserByUsername(user.getUsername()) != null) {
            bindingResult.addError(
                new FieldError(
                    "user",
                    "username",
                    "Username already exists"
                )
            );
        }

        if (user.getPassword().length() <= 8 || user.getPassword().length() >= 32) {
            bindingResult.addError(
                new FieldError(
                    "user",
                    "password",
                    "Password must be 8-32 characters"
                )
            );
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            bindingResult.addError(
                new FieldError(
                    "user",
                    "passwordConfirm",
                    "Password check does not match"
                )
            );
        }

        return bindingResult;
    }

    @Transactional
    public void save(RegisteredUser registeredUser) {
        this.list.add(registeredUser);
        this.registeredUserRepository.save(registeredUser);
    }

    public RegisteredUser getUserById(Long id) {
        Optional<RegisteredUser> user = this.list.stream().filter(registeredUser -> registeredUser.getId() == id)
            .findFirst();

        return user.isPresent() ? user.get() : this.registeredUserRepository.findOne(id);
    }

    public void deleteUserById(Long id) {
        this.list.removeIf(registeredUser -> registeredUser.getId() == id);
        this.registeredUserRepository.delete(id);
    }

    public Set<RegisteredUser> findAll() {
        if (this.list == null || this.list.isEmpty())
            this.syncUserList();
        return this.list;
    }

    private void syncUserList() {
        this.list = Sets.newHashSet(this.registeredUserRepository.findAll());
    }

    public RegisteredUser getUserByUsername(String username) {
        try {
            return this.list.stream()
                .filter(registeredUser -> registeredUser.getUsername().toLowerCase().equals(username.toLowerCase()))
                .collect(onlyElement());
        } catch (Exception e) {
            return null;
        }
    }
}
