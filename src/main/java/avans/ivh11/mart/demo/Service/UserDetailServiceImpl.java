package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.Role;
import avans.ivh11.mart.demo.Repository.RegisteredUserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

//@Service
public class UserDetailServiceImpl { //implements UserDetailsService {

//    @Autowired
//    private RegisteredUserJPA userRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        RegisteredUser user = userRepository.findByUsername(username);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
//    }
}
