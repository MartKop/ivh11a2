package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.Login;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import avans.ivh11.mart.demo.Repository.RegisteredUserRepository;
import avans.ivh11.mart.demo.Repository.UnregisteredUserRepository;
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

import java.util.*;

@Service
public class UserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private UnregisteredUserRepository unregisteredUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Set<RegisteredUser> list;

    /**
     * Add a role to a new registered user and encrypt his password. Save him in the db
     *
     * @param registeredUser
     */
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

    /**
     * Add a unregistered user to the database
     *
     * @param unregisteredUser
     */
    @Transactional
    public void saveUnregistered(UnregisteredUser unregisteredUser){
        this.unregisteredUserRepository.save(unregisteredUser);
    }

    /**
     * Find a registered user by Id
     *
     * @param id
     * @return
     */
    public RegisteredUser getUserById(Long id) {
        this.syncUserList();
        Optional<RegisteredUser> user = this.list.stream().filter(registeredUser -> registeredUser.getId() == id)
            .findFirst();

        return user.isPresent() ? user.get() : this.registeredUserRepository.findOne(id);
    }

    /**
     * Delete a registered user
     *
     * @param id
     */
    public void deleteUserById(Long id) {
        this.syncUserList();
        this.list.removeIf(registeredUser -> registeredUser.getId() == id);
        this.registeredUserRepository.delete(id);
    }

    /**
     * Update the list of registered users and return them
     *
     * @return
     */
    public Set<RegisteredUser> findAll() {
        this.syncUserList();
        return this.list;
    }

    /**
     * Synchronize the list
     */
    private void syncUserList() {
        this.list = Sets.newHashSet(this.registeredUserRepository.findAll());
    }

    /**
     * find a user based on username or return null
     *
     * @param username
     * @return
     */
    public RegisteredUser getUserByUsername(String username) {
        this.syncUserList();
        try {
            return this.list.stream()
                .filter(registeredUser -> registeredUser.getUsername().toLowerCase().equals(username.toLowerCase()))
                .findAny().orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * find a user based on email address or return null
     *
     * @param email
     * @return
     */
    public RegisteredUser getUserByEmail(String email) {
        this.syncUserList();
        try {
            return this.list.stream()
                .filter(registeredUser -> registeredUser.getEmail().toLowerCase().equals(email.toLowerCase()))
                .findFirst().orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * check the login request and add errors to the bindingResult if invalid attempt
     *
     * @param login
     * @param bindingResult
     * @return
     */
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

    /**
     * custom user validation constraints check
     *
     * @param user
     * @param bindingResult
     * @return
     */
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

    /**
     * log the user in
     *
     * @param user
     */
    public void loginUser(RegisteredUser user) {
        List<GrantedAuthority> authorities = this.getAuthorities(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, "credentials?", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * find the authorities based on user_role
     *
     * @param user
     * @return
     */
    private List<GrantedAuthority> getAuthorities(RegisteredUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (user.getRole()) {
            case RegisteredUser.ROLE_SUPER_ADMIN:
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_SUPER_ADMIN));
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_ADMIN));
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_USER));
                break;

            case RegisteredUser.ROLE_ADMIN:
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_ADMIN));
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_USER));
                break;

            case RegisteredUser.ROLE_USER:
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_USER));
                break;

            default:
                authorities.add(new SimpleGrantedAuthority(RegisteredUser.ROLE_USER));
        }

        return authorities;
    }

    /**
     * find the current logged in user
     *
     * @return
     */
    public RegisteredUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof RegisteredUser) {
            return this.registeredUserRepository.findOne(((RegisteredUser) principal).getId());
        }

        return null;
    }
}
