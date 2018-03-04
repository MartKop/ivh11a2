package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.Role;
import avans.ivh11.mart.demo.Repository.RegisteredUserJPA;
import avans.ivh11.mart.demo.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

//    @Autowired
//    private RegisteredUserJPA userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    public void save(RegisteredUser user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//
//        Iterable<Role> roles = roleRepository.findAll();
//        Role role = StreamSupport.stream(roles.spliterator(), false).filter(x -> x.getName().equals("USER")).findFirst().orElse(null);
//
//        user.setRoles((Set) roles);
//        userRepository.save(user);
//    }
//
//    @Override
//    public RegisteredUser findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
}