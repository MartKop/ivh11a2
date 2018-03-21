package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Login;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.thymeleaf.extras.springsecurity4.auth.Authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Set<RegisteredUser> list;

    @Transactional
    public void save(RegisteredUser registeredUser) {
        this.syncUserList();
        if (registeredUser.getRole() == null) {
            registeredUser.setRole("ROLE_USER");
        }
        registeredUser.setPassword(this.bCryptPasswordEncoder.encode(registeredUser.getPassword()));
        this.list.add(registeredUser);
        this.registeredUserRepository.save(registeredUser);
    }

    public RegisteredUser getUserById(Long id) {
        this.syncUserList();
        Optional<RegisteredUser> user = this.list.stream().filter(registeredUser -> registeredUser.getId() == id)
            .findFirst();

        return user.isPresent() ? user.get() : this.registeredUserRepository.findOne(id);
    }

    public void deleteUserById(Long id) {
        this.syncUserList();
        this.list.removeIf(registeredUser -> registeredUser.getId() == id);
        this.registeredUserRepository.delete(id);
    }

    public Set<RegisteredUser> findAll() {
        this.syncUserList();
        return this.list;
    }

    private void syncUserList() {
        this.list = Sets.newHashSet(this.registeredUserRepository.findAll());
    }

    public RegisteredUser getUserByUsername(String username) {
        this.syncUserList();
        try {
            return this.list.stream()
                .filter(registeredUser -> registeredUser.getUsername().toLowerCase().equals(username.toLowerCase()))
                .findAny().get();
        } catch (Exception e) {
            return null;
        }
    }

    public RegisteredUser getUserByEmail(String email) {
        this.syncUserList();
        try {
            return this.list.stream()
                .filter(registeredUser -> registeredUser.getEmail().toLowerCase().equals(email.toLowerCase()))
                .findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }

    public BindingResult loginUserResult(Login login, BindingResult bindingResult) {
        RegisteredUser user = this.getUserByUsername(login.getUsername());
        if (user == null) {
            bindingResult.addError(
                new FieldError(
                    "login",
                    "username",
                    "Username was not found"
                )
            );

            return bindingResult;
        }

        if (!this.bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {
            bindingResult.addError(
                new FieldError(
                    "login",
                    "password",
                    "Password is incorrect"
                )
            );

            return bindingResult;
        }

        return bindingResult;
    }

    public BindingResult validateUser(RegisteredUser user, BindingResult bindingResult) {
        if (user.getUsername().length() < 6 || user.getUsername().length() >32) {
            bindingResult.addError(
                new FieldError(
                    "user",
                    "username",
                    "Username size must be 6 to 32 characters"
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

        if (this.getUserByEmail(user.getEmail()) != null) {
            bindingResult.addError(
                new FieldError(
                    "user",
                    "email",
                    "Email already exists"
                )
            );
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
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

    public void loginUser(RegisteredUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole() != null ? user.getRole() : "ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
