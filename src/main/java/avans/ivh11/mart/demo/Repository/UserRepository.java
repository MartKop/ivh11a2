package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.BaseUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private BaseUserRepository<BaseUser> baseUserBaseUserRepository;
}
