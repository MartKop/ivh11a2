package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UnregisteredUserRepository extends CrudRepository<UnregisteredUser, Long> {

}
