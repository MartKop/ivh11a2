package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface BaseUserRepository<T extends BaseUser> extends CrudRepository<T, Long> {

}
