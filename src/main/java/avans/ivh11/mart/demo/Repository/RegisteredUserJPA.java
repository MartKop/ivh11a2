package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserJPA extends JpaRepository<RegisteredUser, Long> {
    RegisteredUser findByUsername(String username);
}
