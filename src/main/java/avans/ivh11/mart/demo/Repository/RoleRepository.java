package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.StreamSupport;

public interface RoleRepository extends CrudRepository<Role, Long> {

//    public Role findByName(String name) {
//        Iterable<Role> roles = findAll();
//        return StreamSupport.stream(roles.spliterator(), false).filter(x -> x.getName().equals(name)).findFirst().orElse(null);
//    }

}
