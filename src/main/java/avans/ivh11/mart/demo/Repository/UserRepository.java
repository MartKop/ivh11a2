package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmail(String email);
}
