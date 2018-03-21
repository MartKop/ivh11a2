package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public abstract class LoginRepo { //implements RegisteredUserRepository {

//    @Override
//    public RegisteredUser findByUsername(String username) {
//        Iterable<RegisteredUser> users = this.findAll();
//
//        if(StreamSupport.stream(users.spliterator(), false).filter(x -> x.getUsername().equals(username)).count() != 1) {
//            throw new UsernameNotFoundException("Username was not found");
//        }
//
//        return StreamSupport.stream(users.spliterator(), false).filter(x -> x.getUsername().equals(username)).findFirst().get();
//    }
}
