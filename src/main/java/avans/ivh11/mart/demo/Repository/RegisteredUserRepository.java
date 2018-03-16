package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long> {

    RegisteredUser findByUsername(String username);
}
